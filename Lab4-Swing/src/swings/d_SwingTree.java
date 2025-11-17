package swings;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class d_SwingTree {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTree and JTabbedPane Demo");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Subjects");
    
        DefaultMutableTreeNode programming = new DefaultMutableTreeNode("Programming");
        programming.add(new DefaultMutableTreeNode("Java"));
        programming.add(new DefaultMutableTreeNode("Python"));
        programming.add(new DefaultMutableTreeNode("C++"));
        
        DefaultMutableTreeNode web = new DefaultMutableTreeNode("Web");
        web.add(new DefaultMutableTreeNode("HTML"));
        web.add(new DefaultMutableTreeNode("CSS"));
        web.add(new DefaultMutableTreeNode("JavaScript"));
        
        root.add(programming);
        root.add(web);

        JTree tree = new JTree(root);
        JScrollPane treePane = new JScrollPane(tree);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tree View", treePane);
        
        JTextArea textArea = new JTextArea("Welcome to the Notes Tab!");
        tabbedPane.addTab("Notes", new JScrollPane(textArea));

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
