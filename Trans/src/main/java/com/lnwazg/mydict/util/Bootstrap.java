package com.lnwazg.mydict.util;

import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.lnwazg.dbkit.jdbc.MyJdbc;
import com.lnwazg.dbkit.utils.DbKit;
import com.lnwazg.kit.executor.ExecMgr;
import com.lnwazg.kit.handlerseq.HandlerSequence;
import com.lnwazg.kit.log.Logs;
import com.lnwazg.kit.singleton.B;
import com.lnwazg.kit.swing.SwingUtils;
import com.lnwazg.mydict.ui.HandlePanel;
import com.lnwazg.mydict.ui.ImageDialog;
import com.lnwazg.mydict.ui.SrcPanel;
import com.lnwazg.mydict.ui.TargetPannel;
import com.lnwazg.mydict.ui.TranslateFrame;
import com.lnwazg.mydict.ui.WordPanel;
import com.lnwazg.mydict.util.handler.start.DownloadDictTask;
import com.lnwazg.mydict.util.handler.start.InitUserDir;
import com.lnwazg.mydict.util.handler.start.LoadLevel;
import com.lnwazg.mydict.util.handler.start.LoadTemplateConfig;
import com.lnwazg.mydict.util.handler.start.LoadToggleBtnStates;
import com.lnwazg.mydict.util.handler.start.LoadWordBook;
import com.lnwazg.mydict.util.handler.start.RegHotKey;
import com.lnwazg.mydict.util.handler.start.StartLocalHttpServer;

public class Bootstrap
{
    public static void prepareEnv()
    {
        SwingUtils.showEnv();
        SwingUtils.patchJdkDictAppImeBug();
        initSwingBeautyUIConfigs();
        HandlerSequence.getInstance()
            .exec(B.g(InitUserDir.class));
    }
    
    public static void initLog()
    {
        Logs.TIMESTAMP_LOG_SWITCH = true;
    }
    
    public static void initDB()
    {
        DbKit.SQL_MONITOR = true;
        MyJdbc jdbc = DbKit.getJdbc("jdbc:sqlite://" + Constant.USER_DIR + "dict.db", "", "");
        //注册jdbc实例
        B.s(MyJdbc.class, jdbc);
        // 自动初始化表结构
        DbKit.packageSearchAndInitTables("com.lnwazg.mydict.entity");
    }
    
    public static void initUtils()
    {
        HandlerSequence.getInstance()
            .exec(B.g(LoadTemplateConfig.class))
            .exec(B.g(StartLocalHttpServer.class));
    }
    
    public static void initUI()
    {
        ExecMgr.guiExec.execute(new Runnable()
        {
            @Override
            public void run()
            {
                buildUI();
            }
        });
    }
    
    private static void initSwingBeautyUIConfigs()
    {
        try
        {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static void buildUI()
    {
        WinMgr.translateFrame = new TranslateFrame();
        WinMgr.translateFrame.setTitle(Constant.TITLE_REMARK);
        WinMgr.translateFrame.setIconImage(IconMgr.iconHeavy);
        
        WinMgr.targetPannel = new TargetPannel();
        WinMgr.srcPannel = new SrcPanel();
        WinMgr.handlePanel = new HandlePanel();
        WinMgr.wordPanel = new WordPanel();
        
        BoxLayout boxLayout = new BoxLayout(WinMgr.translateFrame.getContentPane(), BoxLayout.X_AXIS);
        WinMgr.translateFrame.setLayout(boxLayout);
        
        WinMgr.leftPanel = new JPanel();
        BoxLayout leftBoxlLayout = new BoxLayout(WinMgr.leftPanel, BoxLayout.Y_AXIS);
        WinMgr.leftPanel.setLayout(leftBoxlLayout);
        WinMgr.leftPanel.add(WinMgr.srcPannel);
        WinMgr.leftPanel.add(Box.createVerticalStrut(10));
        WinMgr.leftPanel.add(WinMgr.handlePanel);
        WinMgr.leftPanel.add(Box.createVerticalGlue());
        WinMgr.leftPanel.add(WinMgr.targetPannel);
        
        WinMgr.translateFrame.add(WinMgr.leftPanel);
        WinMgr.translateFrame.add(Box.createHorizontalGlue());
        WinMgr.translateFrame.add(WinMgr.wordPanel);
        
        WinMgr.translateFrame.setVisible(true);
        WinMgr.translateFrame.setResizable(false);
        
        WinMgr.translateFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                WinMgr.translateFrame.setExtendedState(Frame.ICONIFIED);//最小化，并且不可见了
            }
            
            @Override
            public void windowIconified(WindowEvent e)
            {
                WinMgr.translateFrame.setVisible(false);//先隐藏图标
            }
        });
        
        WinMgr.translateFrame.pack();
        
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int left = (screenWidth - WinMgr.translateFrame.getSize().width) / 2;
        int top = (screenHeight - WinMgr.translateFrame.getSize().height) / 2;
        WinMgr.translateFrame.setLocation(left, top);//设置窗口居中显示
        
        loadSystemTray();
        
        WinMgr.imageDialog = new ImageDialog();
        
        HandlerSequence.getInstance()
            .exec(B.g(RegHotKey.class))
            .exec(B.g(LoadWordBook.class))
            .exec(B.g(LoadToggleBtnStates.class))
            .exec(B.g(LoadLevel.class))
            //只会在首启时拖慢网速，因此决定弃用
            .exec(B.g(DownloadDictTask.class));
    }
    
    /**
     * 加载系统托盘图标设置
     */
    private static void loadSystemTray()
    {
        try
        {
            if (SystemTray.isSupported())
            {// 判断当前平台是否支持系统托盘
                SystemTray st = SystemTray.getSystemTray();
                TrayIcon trayIcon = new TrayIcon(IconMgr.icon);
                String toolTips = Constant.TITLE_REMARK
                    + " 残障英语终结者 v1.0\n让apache.org上面的原生文档变得和蔼可亲！\n让英文文档的阅读易如反掌！\n内含“艾宾浩斯记忆曲线”引擎v1.0\n内含“反囫囵吞枣”系统v1.0\n一切皆动态调整，享受全自动化的单词学习体验！";
                trayIcon.setToolTip(toolTips);//托盘图标提示
                //左击该托盘图标，则打开窗体
                trayIcon.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        //当左击窗口时
                        if (e.getButton() == MouseEvent.BUTTON1)
                        {
                            WinMgr.translateFrame.setVisible(true);//设置窗口可见
                            WinMgr.translateFrame.setExtendedState(Frame.NORMAL);//正常显示窗口
                        }
                    }
                });
                PopupMenu popupMenu = new PopupMenu();
                MenuItem exitSubMenu = new MenuItem("Exit");
                exitSubMenu.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        System.exit(0);
                    }
                });
                popupMenu.add(exitSubMenu);
                trayIcon.setPopupMenu(popupMenu); // 为托盘添加右键弹出菜单
                st.add(trayIcon);//将托盘图标加入到系统托盘中
            }
        }
        catch (Exception e)
        {
            Logs.e(e);
        }
    }
    
}
