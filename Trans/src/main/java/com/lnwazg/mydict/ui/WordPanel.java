package com.lnwazg.mydict.ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.lnwazg.mydict.util.DictDimens;

public class WordPanel extends JPanel
{
    private static final long serialVersionUID = -6300590826634138504L;
    
    private JTable table;
    
    public WordPanel()
    {
        setBorder(BorderFactory.createTitledBorder("念念不忘，必有回响"));
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane);
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(300, DictDimens.WORDBOOK_HEIGHT));
        scrollPane.setViewportView(table);
    }
    
    public JTable getTable()
    {
        return table;
    }
}
