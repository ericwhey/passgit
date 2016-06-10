/* 
 * Copyright (C) 2016 Eric Hey
 *
 * This file is part of passGit.
 *
 * passGit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * passGit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with passGit.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.passgit.app;

import com.passgit.app.file.Value;
import com.passgit.app.repository.dialog.OpenRepositoryDialog;
import com.passgit.app.repository.dialog.InitializeRepositoryPanel;
import com.passgit.app.file.dialog.EditFileDialog;
import com.passgit.app.repository.model.PathModel;
import com.passgit.app.repository.model.PathModelTreeModel;
import com.passgit.app.repository.digest.HmacSHA1MACDigester;
import com.passgit.app.repository.cryptography.AES128Cryptography;
import com.passgit.app.file.format.Base64EncodedEncryptedPropertiesFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.prefs.Preferences;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.passgit.app.file.Format;
import com.passgit.app.repository.Cryptography;
import com.passgit.app.repository.Digest;
import com.passgit.app.repository.Password;
import com.passgit.app.repository.dialog.NewRepositoryDialog;
import com.passgit.app.repository.dialog.NewRepositoryPanel;
import com.passgit.app.repository.password.DefaultPassword;
import java.nio.file.LinkOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author Eric Hey
 */
public class PassGit {

    public static String PROPERTIES_FILENAME = ".passgit.properties";

    private final Controller controller;

    private final Map<WatchKey, Path> keys = new HashMap<WatchKey, Path>();
    private WatchService watcher;

    private Cryptography cryptographer = new AES128Cryptography();
    private Digest digester = new HmacSHA1MACDigester();
    private Format fileFormat = new Base64EncodedEncryptedPropertiesFormat();
    private Password passwordPreparer = new DefaultPassword();

    private byte[] digest;

    private Path rootPath;
    private PathModel rootPathModel;
    private PathModelTreeModel pathModelTreeModel;

    private String keyFileFilename = null;

    private OpenRepositoryDialog repositoryDialog;
    private NewRepositoryDialog newRepositoryDialog;
    private MainFrame mainFrame;

    private Repository gitRepository;

    private Properties properties;

    private Preferences preferences;

    public Controller getController() {
        return controller;
    }

    public Cryptography getCryptographer() {
        return cryptographer;
    }

    public Digest getDigester() {
        return digester;
    }

    public byte[] getDigest() {
        return digest;
    }

    public Format getFileFormat() {
        return fileFormat;
    }

    public Map<WatchKey, Path> getKeys() {
        return keys;
    }

    public void setRootPath(Path rootPath) {
        this.rootPath = rootPath;
    }

    public Path getRootPath() {
        return rootPath;
    }

    public PathModel getRootPathModel() {
        return rootPathModel;
    }

    public PathModelTreeModel getPathModelTreeModel() {
        return pathModelTreeModel;
    }

    private final ServiceLoader<Format> fileFormatLoader = ServiceLoader.load(Format.class);
    private final ServiceLoader<Cryptography> cryptographerLoader = ServiceLoader.load(Cryptography.class);

    public ServiceLoader<Format> getFileFormatLoader() {
        return fileFormatLoader;
    }

    public ServiceLoader<Cryptography> getCryptographerLoader() {
        return cryptographerLoader;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PassGit app = new PassGit();
        if (args.length > 0) {
            app.setRootPath(FileSystems.getDefault().getPath(args[0]));
        }
        app.run();
    }

    public PassGit() {
        controller = new Controller(this);

        preferences = Preferences.userRoot().node(this.getClass().getName());

        String root = preferences.get("root", null);
        String keyfile = preferences.get("keyfile", null);

        if (root != null) {
            rootPath = FileSystems.getDefault().getPath(root);
        } else {
            rootPath = FileSystems.getDefault().getPath(System.getProperty("user.home") + File.separator + "pass");
        }

        if (keyfile != null) {
            keyFileFilename = keyfile;
        }
    }

    public void run() {

        openMainFrame();

        openRepositoryDialog();

    }

    public void openRepositoryDialog() {
        repositoryDialog = new OpenRepositoryDialog(controller, rootPath, keyFileFilename);

        repositoryDialog.setSize(400, 200);
        repositoryDialog.setLocationRelativeTo(null);
        repositoryDialog.setVisible(true);

        repositoryDialog.setFocus();
    }

    public boolean repositoryExists() {
        return rootPath.toFile().exists();
    }

    public boolean openCreateRepositoryDirectoryDialog() {
        int createDirectoryResult = JOptionPane.showConfirmDialog(repositoryDialog, "Directory does not exist.  Create directory?", "Repository Not Found", JOptionPane.OK_CANCEL_OPTION);

        if (createDirectoryResult == JOptionPane.OK_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    public boolean createRepositoryDirectory(Path rootPath) {
        boolean mkdirsResult = rootPath.toFile().mkdirs();

        return mkdirsResult;
    }

    public boolean isRepositoryIntialized() {
        properties = getProperties();

        return properties != null;
    }

    public void openNewRepositoryDialog() {

        newRepositoryDialog = new NewRepositoryDialog(this);

        newRepositoryDialog.pack();
        newRepositoryDialog.setLocationRelativeTo(null);
        newRepositoryDialog.setVisible(true);

    }

    public boolean newRepository(char[] password, File keyFile, Format fileFormat, Cryptography cryptographer, boolean init) {

        byte[] key;

        if (init) {
            try {
                Git git = Git.init().setDirectory(rootPath.toFile()).call();
                gitRepository = git.getRepository();
            } catch (GitAPIException ex) {
                Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        key = passwordPreparer.getKey(password, keyFile);

        if (key != null) {

            initCryptography(key);

            properties = saveProperties();

            preferences.put("root", rootPath.toString());

            if (keyFile != null) {
                preferences.put("keyfile", keyFile.getAbsolutePath());
            } else {
                preferences.remove("keyfile");
            }

            newRepositoryDialog.setVisible(false);

            return true;
        }

        return false;

    }

    public boolean initializeRepository(char[] password, File keyFile) {
        InitializeRepositoryPanel initializeRepositoryPanel = new InitializeRepositoryPanel(this);

        int newRepositoryPanelResult = JOptionPane.showConfirmDialog(repositoryDialog, initializeRepositoryPanel, "Initialize Repository", JOptionPane.OK_CANCEL_OPTION);

        if (newRepositoryPanelResult == JOptionPane.OK_OPTION) {

            fileFormat = initializeRepositoryPanel.getFileFormat();
            cryptographer = initializeRepositoryPanel.getCryptographer();

            if (initializeRepositoryPanel.getInit()) {
                try {
                    Git git = Git.init().setDirectory(rootPath.toFile()).call();
                    gitRepository = git.getRepository();
                } catch (GitAPIException ex) {
                    Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            byte[] key = passwordPreparer.getKey(password, keyFile);

            if (key != null) {
                initCryptography(key);

                properties = saveProperties();

                preferences.put("root", rootPath.toString());

                if (keyFile != null) {
                    preferences.put("keyfile", keyFile.getAbsolutePath());
                } else {
                    preferences.remove("keyfile");
                }

                repositoryDialog.setVisible(false);

                return true;
            }

        }

        return false;
    }

    public boolean openRepository(char[] password, File keyFile) {

        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            gitRepository = builder.setMustExist(true).setWorkTree(rootPath.toFile()).build();

            cryptographer = (Cryptography) Class.forName(properties.getProperty("cryptographer")).newInstance();
            fileFormat = (Format) Class.forName(properties.getProperty("format")).newInstance();

            byte[] key = passwordPreparer.getKey(password, keyFile);

            if (key != null) {
                initCryptography(key);

                byte[] propertiesDigest = getCryptographer().decrypt(new BASE64Decoder().decodeBuffer(properties.getProperty("mac")));

                if (arraysEqual(digest, propertiesDigest)) {

                    preferences.put("root", rootPath.toString());
                    if (keyFile != null) {
                        preferences.put("keyfile", keyFile.getAbsolutePath());
                    } else {
                        preferences.remove("keyfile");
                    }

                    repositoryDialog.setVisible(false);

                    return true;

                } else {

                    JOptionPane.showMessageDialog(repositoryDialog, "Password digest does not match password digest stored in repository properties.");

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void showRepository() {

        try {
            watcher = FileSystems.getDefault().newWatchService();

            registerAll(rootPath);

            rootPathModel = new PathModel(rootPath);

            pathModelTreeModel = new PathModelTreeModel(rootPathModel);

            mainFrame.openRepository(pathModelTreeModel);

            new GitStatusThread(this).start();

            runWatcherThread();
        } catch (IOException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isGitRepository() {
        return gitRepository != null;
    }

    public void initCryptography(byte[] password) {
        cryptographer.init(password);

        digester.init(password);
        digest = digester.getDigest(password);
    }

    public void closeRepository() {
        mainFrame.closeRepository();
    }

    public void commitRepository() {

        try (Git git = new Git(gitRepository)) {

            boolean propertiesUpdated = false;

            Status status = git.status().call();
            for (String filename : status.getUntracked()) {
                if (filename.equals(PROPERTIES_FILENAME)) {
                    propertiesUpdated = true;
                }
            }

            for (String filename : status.getModified()) {
                if (filename.equals(PROPERTIES_FILENAME)) {
                    propertiesUpdated = true;
                }
            }

            if (propertiesUpdated) {
                int result = JOptionPane.showConfirmDialog(mainFrame, "Repository properties changed, add to commit?");
                if (result == JOptionPane.OK_OPTION) {
                    git.add().addFilepattern(PROPERTIES_FILENAME).call();
                } else if (result == JOptionPane.CANCEL_OPTION) {
                    return;
                }

            }

            String message = JOptionPane.showInputDialog("Commit message");

            RevCommit commitResult = git.commit().setMessage(message).call();
        } catch (GitAPIException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pushRepository() {

    }

    public void pullRepository() {

    }

    public void convertRepository() {

    }

    public void openMainFrame() {

        mainFrame = new MainFrame(this);

        mainFrame.setSize(400, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

    }

    public void openEditDialog(PathModel pathModel) {
        if (pathModel.isFile()) {

            Map<String, Value> values = loadFileValues(pathModel);

            EditFileDialog dialog = new EditFileDialog(this, mainFrame, pathModel, values);
            dialog.setSize(400, 400);
            dialog.setLocationRelativeTo(mainFrame);
            dialog.setVisible(true);

        }
    }

    public void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.equals(rootPath) && dir.toFile().isHidden()) {

                    return FileVisitResult.SKIP_SUBTREE;

                }

                register(dir);

                return FileVisitResult.CONTINUE;

            }
        });
    }

    public void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        Path prev = keys.get(key);

        if (prev == null) {
            keys.put(key, dir);
        }

    }

    public void runWatcherThread() {

        FilesystemWatcherThread watcherThread = new FilesystemWatcherThread();
        watcherThread.setApp(this);
        watcherThread.setWatcher(watcher);
        watcherThread.start();

    }

    public Properties getProperties() {
        File propertiesFile = rootPath.resolve(".passgit.properties").toFile();

        if (propertiesFile.exists()) {
            try {
                properties = new Properties();

                BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));

                properties.load(reader);

            } catch (IOException ex) {
                Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return properties;
    }

    public Properties saveProperties() {
        Path propertiesPath = rootPath.resolve(".passgit.properties");
        File propertiesFile = propertiesPath.toFile();

        Properties properties = new Properties();

        try {

            properties.put("mac", new BASE64Encoder().encode(getCryptographer().encrypt(digest)));
            properties.put("format", getFileFormat().getClass().getCanonicalName());
            properties.put("cryptographer", getCryptographer().getClass().getCanonicalName());

            FileWriter writer = new FileWriter(propertiesFile);

            properties.store(writer, new Date().toString());
            
            if (System.getProperty("os.name").toLowerCase().indexOf("win") > 0)
                Files.setAttribute(propertiesPath, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
            
        } catch (Exception ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return properties;
    }

    public Map<String, Value> loadFileValues(PathModel pathModel) {
        return fileFormat.loadValues(this, pathModel);
    }

    public void saveFileValues(PathModel pathModel, Map<String, Value> values) {
        fileFormat.saveValues(this, pathModel, values);
    }

    public void updateStatus() {
        Git git = new Git(gitRepository);
        try {
            Status status = git.status().call();
            processStatus(status);
        } catch (GitAPIException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoWorkTreeException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void processStatus(Status status) {
        processStatus(status, rootPathModel);

        for (String filename : status.getMissing()) {
            Path removedPath = rootPath.resolve(filename);
            List<PathModel> recursePath = getRootPathModel().recursePath(removedPath);

            PathModel lastPathModel = recursePath.get(recursePath.size() - 1);

            if (removedPath.equals(lastPathModel.getPath())) {
                lastPathModel.setDeleted(true);

                getPathModelTreeModel().fireChangedEvent(recursePath);
            } else {
                System.out.println("adding already deleted file");
                PathModel childPathModel = new PathModel(removedPath);
                childPathModel.setDeleted(true);

                addChildPathModel(recursePath, lastPathModel, childPathModel);
            }

        }
    }

    private void addChildPathModel(List<PathModel> pathModelList, PathModel parentPathModel, PathModel childPathModel) {
        boolean found = false;

        for (int childIndex = 0; !found && childIndex < parentPathModel.getChildCount(); childIndex++) {
            PathModel siblingPathModel = parentPathModel.getChild(childIndex);

            if (siblingPathModel.toString().compareTo(childPathModel.toString()) > 0) {
                parentPathModel.addChildAt(childPathModel, childIndex);

                getPathModelTreeModel().fireAddEvent(pathModelList, childIndex, childPathModel);

                found = true;
            }
        }

        if (!found) {

            int childIndex = parentPathModel.getChildCount();

            parentPathModel.addChild(childPathModel);

            getPathModelTreeModel().fireAddEvent(pathModelList, childIndex, childPathModel);
        }
    }

    private void processStatus(Status status, PathModel pathModel) {
        boolean untracked = false;

        for (String filename : status.getUntracked()) {
            File untrackedFile = new File(rootPath.toFile(), filename);
            if (untrackedFile.toPath().equals(pathModel.getPath())) {
                untracked = true;
                if (!pathModel.isUntracked()) {
                    pathModel.setUntracked(true);

                    List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                    getPathModelTreeModel().fireChangedEvent(recursePath);
                }
            }
        }

        if (!untracked) {
            if (pathModel.isUntracked()) {
                pathModel.setUntracked(false);

                List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                getPathModelTreeModel().fireChangedEvent(recursePath);
            }
        }

        boolean modified = false;

        for (String filename : status.getModified()) {
            File modifiedFile = new File(rootPath.toFile(), filename);
            if (modifiedFile.toPath().equals(pathModel.getPath())) {
                modified = true;
                if (!pathModel.isModified()) {
                    pathModel.setModified(true);

                    List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                    getPathModelTreeModel().fireChangedEvent(recursePath);
                }
            }
        }

        if (!modified) {
            if (pathModel.isModified()) {
                pathModel.setModified(false);

                List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                getPathModelTreeModel().fireChangedEvent(recursePath);
            }
        }

        boolean added = false;

        for (String filename : status.getAdded()) {
            File addedFile = new File(rootPath.toFile(), filename);
            if (addedFile.toPath().equals(pathModel.getPath())) {
                added = true;
                if (!pathModel.isAdded()) {
                    pathModel.setAdded(true);

                    List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                    getPathModelTreeModel().fireChangedEvent(recursePath);
                }
            }
        }

        if (!added) {
            if (pathModel.isAdded()) {
                pathModel.setAdded(false);

                List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                getPathModelTreeModel().fireChangedEvent(recursePath);
            }
        }

        boolean changed = false;

        for (String filename : status.getChanged()) {
            File changedFile = new File(rootPath.toFile(), filename);
            if (changedFile.toPath().equals(pathModel.getPath())) {
                changed = true;
                if (!pathModel.isChanged()) {
                    pathModel.setChanged(true);

                    List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                    getPathModelTreeModel().fireChangedEvent(recursePath);
                }
            }
        }

        if (!changed) {
            if (pathModel.isChanged()) {
                pathModel.setChanged(false);

                List<PathModel> recursePath = getRootPathModel().recursePath(pathModel.getPath());

                getPathModelTreeModel().fireChangedEvent(recursePath);
            }
        }

        for (int i = 0; i < pathModel.getChildCount(); i++) {
            processStatus(status, pathModel.getChild(i));
        }
    }

    public static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    public static boolean arraysEqual(byte[] array1, byte[] array2) {
        if (array1.length != array2.length) {
            return false;
        }

        boolean same = true;

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                same = false;
            }
        }

        return same;
    }

    public void addDirectory(PathModel parentPathModel) {
        String inputValue = JOptionPane.showInputDialog("Directory Name");
        File file = new File(parentPathModel.getFile(), inputValue);
        file.mkdir();

        PathModel childPathModel = new PathModel(file.toPath());

        List<PathModel> recursePath = getRootPathModel().recursePath(file.toPath());

        addChildPathModel(recursePath, parentPathModel, childPathModel);

    }

    public void addFile(PathModel parentPathModel) {
        String inputValue = JOptionPane.showInputDialog("Filename");

        File file = new File(parentPathModel.getFile(), inputValue);

        try {
            file.createNewFile();

            PathModel childPathModel = new PathModel(file.toPath());

            List<PathModel> recursePath = getRootPathModel().recursePath(file.toPath());

            addChildPathModel(recursePath, parentPathModel, childPathModel);

        } catch (IOException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyPasswordToClipboard(PathModel pathModel) {
        Value value = loadFileValues(pathModel).get("password");
        String password = value.getString();

        try {

            StringSelection stringSelection = new StringSelection(password);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

        } catch (Exception e) {

        }

    }

    public void delete(PathModel pathModel) {
        int sure = JOptionPane.showConfirmDialog(mainFrame, "Are you sure?");
        if (sure == JOptionPane.OK_OPTION) {
            try {
                Files.delete(pathModel.getPath());

                List<PathModel> pathModelList = getRootPathModel().recursePath(pathModel.getPath());
                pathModel.setDeleted(true);

                getPathModelTreeModel().fireChangedEvent(pathModelList);

            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteFromPathModelTreeModel(PathModel deletedPathModel) {
        int childIndex = -1;

        List<PathModel> recursePath = getRootPathModel().recursePath(deletedPathModel.getPath());

        if (recursePath.size() > 1) {
            PathModel parentPathModel = recursePath.get(recursePath.size() - 2);

            for (int i = 0; i < parentPathModel.getChildCount(); i++) {
                if (parentPathModel.getChild(i).getPath().equals(deletedPathModel.getPath())) {
                    childIndex = i;
                }
            }

            if (childIndex != -1) {
                parentPathModel.removeChild(childIndex);

                List<PathModel> parentPathModelList = recursePath.subList(0, recursePath.size() - 1);

                getPathModelTreeModel().fireRemoveEvent(parentPathModelList, childIndex);
            }

        }
    }

    public void gitAdd(PathModel pathModel) {
        Path relativePath = rootPath.relativize(pathModel.getPath());
        System.out.println(relativePath.toString());
        Git git = new Git(gitRepository);
        try {
            git.add().addFilepattern(relativePath.toString()).call();
        } catch (GitAPIException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gitRemove(PathModel pathModel) {
        Path relativePath = rootPath.relativize(pathModel.getPath());
        System.out.println(relativePath.toString());
        Git git = new Git(gitRepository);
        try {
            git.rm().addFilepattern(relativePath.toString()).call();

            deleteFromPathModelTreeModel(pathModel);
        } catch (GitAPIException ex) {
            Logger.getLogger(PassGit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
