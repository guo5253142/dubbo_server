package com.my.common.tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

public class SftpUtil {

    String _root = "/";
    com.jcraft.jsch.Session _sshSession;

    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    public ChannelSftp connect(String host, int port, String username, String password, String root) throws Exception {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            _sshSession = jsch.getSession(username, host, port);
            _sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            _sshSession.setConfig(sshConfig);
            _sshSession.connect();
            Channel channel = _sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("链接到SFTP成功：" + host);
            // _root += root;
            sftp.cd(root);
            return sftp;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 关闭sftp链接
     *
     * @param sftp
     */
    public void disconnect(ChannelSftp sftp) {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            } else if (sftp.isClosed()) {
            }
        }
        if (_sshSession != null && _sshSession.isConnected()) {
            _sshSession.disconnect();
        }
        System.out.println("sftp 已经关闭");
    }

    private static Vector _list(String dir, ChannelSftp sftp) {
        try {
            return sftp.ls(dir);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static Vector _list(ChannelSftp sftp) {
        try {
            return sftp.ls(sftp.pwd());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 列出目录下的文件
     * <p>
     * 要列出的目录
     *
     * @param sftp
     * @return
     * @throws SftpException
     */

    public ArrayList getDirlist(ChannelSftp sftp) {
        try {
            Vector ls = _list(sftp);
            return _buildFiles(ls);
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList _buildFiles(Vector ls) throws Exception {
        if (ls != null && ls.size() >= 0) {
            ArrayList<String> list = new ArrayList();
            for (int i = 0; i < ls.size(); i++) {
                LsEntry f = (LsEntry) ls.get(i);
                String nm = f.getFilename();
                if (nm.equals(".") || nm.equals(".."))
                    continue;
                SftpATTRS attr = f.getAttrs();
                Map m = new HashMap();
                if (attr.isDir()) {
                    m.put("dir", new Boolean(true));
                } else {
                    m.put("dir", new Boolean(false));
                }
                m.put("name", nm);
                list.add(nm);
            }
            return list;
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp
     */
    public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        InputStream is = null;
        FileWriter fileWr = null;
        try {
            sftp.cd(directory);
            fileWr = new FileWriter(saveFile);
            // sftp.get(downloadFile, saveFile);
            is = sftp.get(downloadFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sBuilder.append(line + "\r\n");
            }
            fileWr.write(sBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fileWr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp
     */
    public void download(String directory, String downloadFile, String saveFilePath, String saveFile, ChannelSftp sftp)
            throws Exception {
        InputStream is = null;
        FileWriter fileWr = null;
        try {
            Vector vect = this._list(directory + downloadFile, sftp);
            if (vect == null) {
                System.out.println("【" + directory + downloadFile + "】文件在服务器中不存在，请检查或稍后处理！");
                return;
            } else {
                sftp.cd(directory);
                // saveFilePath = saveFilePath.replace("{$CurrentDate}", date);
                // 判断文件夹是否存在
                File folder = new File(saveFilePath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                sftp.get(directory + downloadFile, saveFilePath + saveFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public void uploadFile(String directory, String uploadFile, String saveFile, String mkdirName, ChannelSftp sftp)
            throws Exception {
        String[] fileName = null;
        try {
            sftp.cd(directory);
            fileName = mkdirName.split("/");
            for (int i = 0; i < fileName.length; i++) {
                try {
                    sftp.ls(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                } catch (Exception e) {
                    sftp.mkdir(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                }
            }
            sftp.put(uploadFile, saveFile);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void uploadFile(String directory, File uploadFile, String saveFile, String mkdirName, ChannelSftp sftp)
            throws Exception {
        String[] fileName = null;
        try {
            sftp.cd(directory);
            fileName = mkdirName.split("/");
            for (int i = 0; i < fileName.length; i++) {
                try {
                    sftp.ls(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                } catch (Exception e) {
                    sftp.mkdir(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                }
            }
            InputStream is = new BufferedInputStream(new FileInputStream(uploadFile));
            sftp.put(is, saveFile);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void uploadFile(String directory, InputStream inputStream, String saveFile, String mkdirName,
                           ChannelSftp sftp) throws Exception {
        String[] fileName = null;
        try {
            sftp.cd(directory);
            fileName = mkdirName.split("/");
            for (int i = 0; i < fileName.length; i++) {
                try {
                    sftp.ls(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                } catch (Exception e) {
                    sftp.mkdir(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                }
            }
            sftp.put(inputStream, saveFile);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void mkdir(String directory, String fileNames, ChannelSftp sftp) {
        String[] fileName = null;
        try {
            sftp.cd(directory);
            fileName = fileNames.split("/");
            for (int i = 0; i < fileName.length; i++) {
                try {
                    sftp.ls(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                } catch (Exception e) {
                    sftp.mkdir(fileName[i]);
                    directory = directory + "/" + fileName[i];
                    sftp.cd(directory);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws Exception {
        new SftpUtil().test_upload();
    }

    //列出目录
    private void test_list() throws Exception {

        SftpUtil sftp = new SftpUtil();
        String host = "114.251.243.97";
        int port = 20000;
        String username = "xlfqtest";
        String password = "4X4932yC";
        String root = "/XLFQ01/";
        ChannelSftp chsftp = sftp.connect(host, port, username, password, root);

        List list = sftp._buildFiles(chsftp.ls("/XLFQ01/receive/10/"));

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        sftp.disconnect(chsftp);

    }

    private void test_upload() throws Exception {

        SftpUtil sftp = new SftpUtil();
        String host = "114.251.243.97";
        int port = 20000;
        String username = "xlfqtest";
        String password = "4X4932yC";
        String root = "/XLFQ01/";
        ChannelSftp chsftp = sftp.connect(host, port, username, password, root);


        sftp.uploadFile("/XLFQ01/receive/80", "D:\\logs\\zzz.rar", "zzz.rar", "20", chsftp);

        sftp.disconnect(chsftp);

    }

    public void uploadFileForWindows(String host, String userName, String password, String uploadDirectory, InputStream inputStream, String fileName) throws Exception {
        FTPClient ftpClient = null;

        try {
            ftpClient = new FTPClient();
            ftpClient.connect(host);
            ftpClient.login(userName, password);
            ftpClient.changeWorkingDirectory(uploadDirectory);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("Unicode");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(fileName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }


        }


    }
}