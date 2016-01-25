import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
class MyEditor implements ActionListener{
	JFrame jf;
	JLabel jl;
	JTextField jtf;
	JTextArea jta,jta1;
	JScrollPane jsp,jsp1;
	Runtime r;
	String str="";
	String fname="";
	String result="";
	String result1="";
	JMenuBar mb;
    JMenu file,edit,help,run;
    JMenuItem jbcompile,jbrun,cut,copy,paste,selectAll;
	MyEditor(){
	     jf=new JFrame("My Editor");
	     jf.setLayout(null);
	     jl=new JLabel("Enter name of class");
	     jtf=new JTextField();
	     jl.setBounds(0,20,130,40);
	     jtf.setBounds(180,20,230,40);
	     ImageIcon imageIcon = new ImageIcon(("C:\\Users\\javed123ahmed\\Desktop\\java.png"));
         jta = new JTextArea(50,50) {

              Image image = imageIcon.getImage();

              Image grayImage = GrayFilter.createDisabledImage(image);
              {
                  setOpaque(false); 
              }
              
              public void paintComponent(Graphics g) {
              g.drawImage(image, 0, 0,getWidth(),getHeight(), this);
              super.paintComponent(g);
              }
         };
	     jta.setBounds(0,60,1370,440);
	     jta.addFocusListener(new MyFocusListener(this));
	     jta1=new JTextArea(100,100);
	     jta.setFont(new Font("varinda",Font.PLAIN,15));
	     jta1.setFont(new Font("varinda",Font.PLAIN,15));
	     jsp=new JScrollPane(jta);
	     jsp1=new JScrollPane(jta1);
	     jsp.setBounds(0,60,1370,440);
	     jsp1.setBounds(0,500,1370,200);
	     jf.add(jsp);
	     jf.add(jsp1);

	     jbcompile=new JMenuItem("Compile");
	     jbrun=new JMenuItem("Run");
	     cut=new JMenuItem("cut");
         copy=new JMenuItem("copy");
         paste=new JMenuItem("paste");
         selectAll=new JMenuItem("selectAll");

         mb=new JMenuBar();
         mb.setBounds(0,0,1370,20);
         file=new JMenu("File");
         edit=new JMenu("Edit");
         help=new JMenu("Help");
         run=new JMenu("run");

         cut.addActionListener(this);
         copy.addActionListener(this);
         paste.addActionListener(this);
         selectAll.addActionListener(this);

	     

         run.add(jbcompile);
         run.add(jbrun);
         edit.add(cut);
         edit.add(copy);
         edit.add(paste);
         edit.add(selectAll);

         mb.add(file);
         mb.add(edit);
         mb.add(help);
         mb.add(run);
         jf.add(mb);
	     //jbcompile=new JButton("Compile");
	     //jbrun=new JButton("Run");
	     //jbcompile.setBounds(100,300,80,25);
	     //jbrun.setBounds(280,300,80,25);
	     jf.add(jl);
	     jf.add(jtf);
	     r=Runtime.getRuntime();
	     jbcompile.addActionListener(this);
	     jbrun.addActionListener(this);
	     //jf.add(jbcompile);
	     //jf.add(jbrun);
	     
	     jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     jf.setSize(550,550);
	     jf.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==cut)
				jta.cut();
		if(e.getSource()==paste)
				jta.paste();
	    if(e.getSource()==copy)
				jta.copy();
		if(e.getSource()==selectAll)
				jta.selectAll();
		if(e.getSource()==jbcompile)
		{
			str="";
			if(!jtf.getText().equals(""))
			{
				try
				{
					fname=jtf.getText().trim()+".java";
					FileWriter fw=new FileWriter(fname);
					String s1=jta.getText();
					PrintWriter pw=new PrintWriter(fw);
					pw.println(s1);
					pw.flush();
					Process error=r.exec("C:\\Program Files\\Java\\jdk1.8.0_45\\bin\\javac.exe -d . "+fname);
					BufferedReader err=new BufferedReader(new InputStreamReader(error.getErrorStream()));
					while(true)
					{
						String temp=err.readLine();
						if(temp!=null)
						{
							result+=temp;
							result+="\n";
						}
						else break;
					}
					if(result.equals(""))
					{
						jta1.setText("Compilation Successful!"+fname);
						err.close();
					}
					else
						jta1.setText(result);
				}
				catch(Exception e1)
				{
					System.out.println(e1);
				}
			}
			else
				jta1.setText("Please Enter java Programme name!");
		}
		if(e.getSource()==jbrun)
		{
			int start=0;
			try
			{
				String fn=jtf.getText().trim();
				Process p=r.exec("C:\\Program Files\\Java\\jdk1.8.0_45\\bin\\java "+fn);
				BufferedReader output=new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while(true)
				{
					String temp=output.readLine();
					if(temp!=null)
					{
						result+=temp;
						result+="\n";
					}
					else break;
				}
				while(true)
				{
					String temp=error.readLine();
					if(temp!=null)
					{
						result1+=temp;
						result1+="\n";
					}
					else break;
				}
				output.close();
				error.close();
				if(!result.equals("")&&result1.equals(""))
					jta1.setText(result);
				if(result.equals(""))
					jta1.setText(result1);
				if(!result.equals("")&&!result1.equals(""))
					jta1.setText(result+"\n"+result1);
			}
			catch(Exception e2)
			{
				System.out.println(e2);
			}
		}
		
	}
	public static void main(String args[])
	{
	     new MyEditor();
	}
}
class MyFocusListener extends FocusAdapter
{
	MyEditor e;
	MyFocusListener(MyEditor e)
	{
		this.e=e;
	}
	public void focusGained(FocusEvent fe)
	{
		String str=e.jtf.getText().trim();
		e.jta.setText("public class "+str+"\n"+"{"+"\n"
			+"public static void main(String args[])"+"\n"
				+"{"+"\n"
				+"     "+"\n"
				+"}"+"\n"
				+"}");
	}
}