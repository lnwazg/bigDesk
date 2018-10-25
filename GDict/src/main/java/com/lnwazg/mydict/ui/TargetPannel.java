package com.lnwazg.mydict.ui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.lnwazg.mydict.util.WinMgr;

public class TargetPannel extends JPanel
{
    private static final long serialVersionUID = 263138656858972409L;
    
    private JTextPane textPane;
    
    private JScrollPane paneScrollPane;
    
    public TargetPannel()
    {
        setBorder(BorderFactory.createTitledBorder("翻译结果"));
        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        textPane.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
            }
            
            @Override
            public void mousePressed(MouseEvent e)
            {
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {
            }
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 3)
                {
                    // 处理鼠标三击
                }
                else if (e.getClickCount() == 2)
                {
                    // 处理鼠标双击
                    WinMgr.imageDialog.showImage();
                }
                else if (e.isMetaDown())
                {
                    //检测鼠标右键单击
                }
            }
        });
        paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setPreferredSize(new Dimension(300, 420));
        add(paneScrollPane);
        
    }
    
    public JTextPane getTextPane()
    {
        return textPane;
    }
}