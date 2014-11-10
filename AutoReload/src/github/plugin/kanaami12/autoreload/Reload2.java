package github.plugin.kanaami12.autoreload;

import java.io.File;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class Reload2 extends JavaPlugin{

    //plugin
    public static Reload2 plugin;
    //false= not day
    public static boolean Day = true;
    //file Name
    public static int[] args;
    //file Name (2
    public static int[] args2;
    //Files
    public static int fileLength;
    //Files(2
    public static int fileLength2;
    //ReloadFIle
    public static File reloadFile;
    //add File
    public static boolean newFile = false;
    //reloadMode
    public static boolean reloadMode = true;
    //Offmode
    public static boolean OffMode = false;
    //Task
    public static int task = 0;

    @Override
    public void onEnable(){
        plugin = this;

        //make folder
        File f = new File("plugins/AutoReload");
        if (!f.exists()) {
            f.mkdirs();
        }

        File file = new File("plugins/AutoReload/Settings.txt");

        Message.onCreate(file);

        //files
        getFiles();

        //'autreload'command
        getCommand("autoreload").setExecutor(new AutoReloadCmd());

        if(task == 0){
        	task = 1;
        	new TimeRunnable().runTaskTimer(this, 20, 20);
        }
    }

      public void onDisable() {
      }

    /**
     *
     */
    public static void getFiles() {
        String path = "plugins";
        File dir = new File(path);
        File[] files = dir.listFiles();
        int i2 = files.length + 1;
        args = new int[i2];
        fileLength = files.length;
        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            onWriteConfig(file, i);
        }
    }

    /**
     *
     * @param file
     * @param i
     */
    @SuppressWarnings("deprecation")
    public static void onWriteConfig(File file, int i){
        //plugin
        final Reload2 Rel = Reload2.plugin;
        long lastModified = file.lastModified();
        Date date = new Date(lastModified);

        int sec = date.getSeconds();

        args[i] = sec;
    }

    public static void getFiles2() {
        //plugins
        String path = "plugins";
        File dir = new File(path);
        File[] files = dir.listFiles();

        args2 = new int[files.length];
        fileLength2 = files.length;
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            onWriteConfig2(file, i);
        }
    }


    /**
     *
     * @param file
     * @param i
     */
    @SuppressWarnings("deprecation")
    public static void onWriteConfig2(File file, int i){
        //plugin
        final Reload2 Rel = Reload2.plugin;

        long lastModified = file.lastModified();
        Date date = new Date(lastModified);

        args2[i] = date.getSeconds();
    }

    /**
     *
     * @param file
     * @param i
     */
    @SuppressWarnings("deprecation")
    public static void onConfig3(File file, int i){
        //plugin
        final Reload2 Rel = Reload2.plugin;

        if(fileLength != fileLength2){
            newFile = true;
            Day = false;
            return;
        }
        if(args[i] == args2[i])
            return;
        if(args[i] != args2[i]){
            Day = false;
            reloadFile = file;
        }
    }

    public static void getFiles3() {
        String path = "plugins";
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            onConfig3(file, i);

        }
    }

    /**
     * player
     */
    public static void sendop(int i){

        if(i == 0){
            //op message
            for(Player player:Bukkit.getServer().getOnlinePlayers())
            {
            	if(player.isOp()){
                    player.sendMessage("§CFound the update of folder.\n"
                            + "§7FileName: " + reloadFile.getName());
                    continue;
            	}
            }
        }
        if(i == 1){
            //op message
                for(Player player2 : Bukkit.getServer().getOnlinePlayers()){
                	if(player2.isOp()){
                		player2.sendMessage("§CReload complete");
                    	continue;
                	}
                }
            }
        }

    /**
     * 1s
     */
    class TimeRunnable extends BukkitRunnable{

    	//file name
        String fileName;

        public void run() {
            if(OffMode){
                //off mode
                Day = true;
                getFiles();
                return;
            }
            if(!reloadMode){
                return;
            }
            if(Day == false){
                File file = new File("plugins/AutoReload/Settings.txt");
                Message.readFile(file);

                if(!newFile){
                	if(reloadFile != null){
                		fileName = "§7FileName: " + reloadFile.getName();
                	}else{
                		fileName = "";
                	}

                }else if(newFile){
                    //plugins new file
                    fileName = "§7File has been added";
                    newFile = false;
                }

                getLogger().info("\n--------------------------------------------\n"
                                + "Found the update of folder.\n"
                                + fileName);
                if(Message.message1)
                    getServer().broadcastMessage("§CFound the update of folder.\n"
                            + fileName);
                //OPに流す が有効の場合
                if(Message.message2)
                sendop(0);

                getFiles();

                Day = true;

                //reloading
                Bukkit.getServer().reload();

                getLogger().info("\n--------------------------------------------\n"
                        + "Reload complete");
                if(Message.message1)
                    getServer().broadcastMessage("§CReload complete");
                if(Message.message2)
                sendop(1);

                final Reload2 Rel = Reload2.plugin;

                Rel.onLoad();
                task = 0;
                cancel();
            }
            getFiles2();

            getFiles3();
        }
    }
}
