package OperatingSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Create by huabin
 * on 2021/8/30 19:25
 */
public class View {
    public static void main(String[] args) {
        Frame f = new Frame("��Դ������������");

        f.setBounds(400, 200, 400, 300);
        //ѡ�񲼾ַ�ʽ
        f.setLayout(new FlowLayout());

        //�����ı���
        TextField tf = new TextField(20);

        //������ť
        Button bu = new Button("���м��㷨");
        Button bu1 = new Button("�������");

        //�����ı���
        TextArea ta = new TextArea(10, 40);

        f.add(tf);
        f.add(bu);
        f.add(bu1);
        f.add(ta);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //����ť���ʵ��
        bu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //��ȡ�ı����е��ı�
                String tf_str = tf.getText().trim();
                tf.setText("");

                //���ı������ı���
                //ta.setText(tf_str);
                //׷���ı�
                ta.append(tf_str+"\r\n");

                //������ƶ���tf�ı���
                tf.requestFocus();
            }
        });

        bu1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //��ȡ�ı����е��ı�
                String tf_str = tf.getText().trim();
                tf.setText("");

                //���ı������ı���
                //ta.setText(tf_str);
                //׷���ı�
                ta.append(tf_str+"\r\n");

                //������ƶ���tf�ı���
                tf.requestFocus();
            }
        });

        //���ô�����ʾ
        f.setVisible(true);
    }
}