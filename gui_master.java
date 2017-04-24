package com.assignment;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.Font;

public class gui_final extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	JButton search;
	JPanel data;
	JLabel browse;
	JButton display;
	JTextField enter;
	JButton Update;
	private JLabel lblWelcomeToThe;
	
	
	ArrayList<String> arraylist1 = new ArrayList<>();
	ArrayList<String> arraylist2 = new ArrayList<>();
	
	
	int badCount = 0;
	float fileWordAmount = 0;
	public Object frame;
	
	
	//running programme
	public static void main(String[] args)
	{
		gui_final gf = new gui_final();
		gf.setVisible(true);
	}

	public gui_final() 
	{
		
		//panel
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		data = new JPanel();
		data.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(data);
		data.setLayout(null);
		
		//label to browse user files 
		browse = new JLabel("Browse Files  To Test");
		browse.setFont(new Font("Impact", Font.PLAIN, 11));
		browse.setBounds(244, 48, 144, 16);
		data.add(browse);
		
		//search button
		search = new JButton("Search");
		search.setFont(new Font("Impact", Font.PLAIN, 11));
		search.setBounds(244, 97, 117, 29);
		data.add(search);
		search.addActionListener(this);
		
		//test button
		display = new JButton("Test");
		display.setFont(new Font("Impact", Font.PLAIN, 11));
		display.setBounds(244, 156, 112, 29);
		data.add(display);
		
		//label
		lblWelcomeToThe = new JLabel("Welcome to the Abusive Content Detector");
		lblWelcomeToThe.setFont(new Font("Impact", Font.PLAIN, 11));
		lblWelcomeToThe.setBounds(110, 11, 196, 16);
		data.add(lblWelcomeToThe);
		
		//add word to file
		JLabel lblAddWordTo = new JLabel("Add Word to File");
		lblAddWordTo.setFont(new Font("Impact", Font.PLAIN, 11));
		lblAddWordTo.setBounds(10, 58, 144, 16);
		data.add(lblAddWordTo);
		
		//update button
		JButton Update = new JButton("Update");
		Update.setFont(new Font("Impact", Font.PLAIN, 11));
		Update.setBounds(10, 156, 117, 29);
		data.add(Update);
		Update.addActionListener(this);
		
		//text field for adding word
		enter = new JTextField();
		enter.setHorizontalAlignment(SwingConstants.CENTER);
		enter.setFont(new Font("Consolas", Font.PLAIN, 11));
		enter.setBounds(10, 100, 117, 26);
		data.add(enter);
		enter.setColumns(10);
		
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(search))
		{
			try 
			{
				search();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		
		if(e.getSource().equals(display))
		{
			
			comparing();
			arraylist2.clear();
				
		}
		if(e.getSource().equals(Update))
		{
			try 
			{
				writeToFile();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		
	} 
	
	

	public void search() throws IOException
	{
		JFileChooser searcher = new JFileChooser();
		searcher.showOpenDialog(null);
		
		File f = searcher.getSelectedFile();
		String search_result= f.getAbsolutePath();
		
		check(search_result);
		
		
	}
	
	public void check(String read) throws IOException
	{
		String file = read ;
		
		

		BufferedReader br = new BufferedReader (new FileReader(file));
		BufferedReader bw = new BufferedReader (new FileReader("badwords.txt"));
		String line = "";
		
		
		try
		{
			while((line = br.readLine()) != null)
			{
				
				
				arraylist1.add(line);
			
				
			}
				
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
			
		
		
		
		try
		{
			while((line = bw.readLine()) != null)
			{
				arraylist2.add(line);
			}
				
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		br.close();
		bw.close();
		
		
		
		
	}
	
	public void writeToFile() throws IOException
	{
		
		try
		{
			String input = enter.getText()+"\n";
	
			Files.write(Paths.get("badwords.txt"), input.getBytes(), StandardOpenOption.APPEND);
		}
		catch (IOException e)
		{
			    //exception handling left as an exercise for the reader
		}
			JOptionPane.showMessageDialog(this, "Your entry has been added");
			enter.setText(null);
		}
		
	
	
	public void comparing()
	{
		
		
		for(String compare : arraylist1)
		{
			
			String[] strcheck = compare.split(" ");
			float temp_var = strcheck.length;
			fileWordAmount += temp_var;
			
			for(int i = 0; i<strcheck.length;i++)
			{
				System.out.println(strcheck[i]);
				System.out.println(i);
				
				for(int j = 0; j<arraylist2.size();j++)
				{
					
					if(strcheck[i].equals(arraylist2.get(j)))
					{
						badCount++;
					}
					else
					{
						
					}
				}
			}
			
			
		}
		
		
		float badPercent = ((badCount/fileWordAmount)*100);
		
		JOptionPane.showMessageDialog(this,"A total of: " +badPercent+  "% of your file contains abusive content");
		
		
		
	}
}
