package com.darkorbit.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.darkorbit.mysql.MySQLManager;
import com.darkorbit.net.GameServer;
import com.darkorbit.utils.Console;

public class Launcher {

	private static final int PORT = 8080;
	public static final String clientVersion = "4.1";
	/***********************************************/
	
	private static String mysqlHost 	= null;
	private static String mysqlUserName = null;
	private static String mysqlPassword = null;
	private static String mysqlDatabase = null;
	
	private static BufferedReader configReader = null;
	public static boolean developmentMode = true;
	private static final String version = "Development version v0.1.7";
	private static Scanner in = new Scanner(System.in);

	/**
	 * Main method. Reads the config file and set-up the server
	 * @param args
	 * @throws IOException - Because of the server initialization
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("DarkOrbit Game Server (C) 2015 - " + version);
		System.out.println("Starting up everything...\n");
		if(developmentMode) {
			Console.alert("Disable by default development mode in official releases!");
		}
		readConfigFile();
		
		Console.out("Connecting to MySQL...");
		new MySQLManager(mysqlHost, mysqlUserName, mysqlPassword, mysqlDatabase);
		
		new GameServer(PORT);
		
		waitForCommands();
	}
	
	/**
	 * Lee el archivo de configuración
	 */
	private static void readConfigFile() {
		Console.out("Reading config file...");
		try {
			configReader = new BufferedReader(new FileReader("config/config.ini"));
			String line;
			while((line=configReader.readLine()) != null) {
				//Si la linea comienza con #, [, lo que sea pasa a la siguiente
				
				if (line.startsWith("[") || line.startsWith("#") || !line.contains("=")) continue;

				String[] values = line.split("=");
				switch(values[0]) {
					case "mysqlHost":
						mysqlHost = values[1];
						break;
						
					case "mysqlUserName":
						mysqlUserName = values[1];
						break;
						
					case "mysqlPassword":
						mysqlPassword = values[1];
						break;
						
					case "mysqlDatabase":
						mysqlDatabase = values[1];
						break;
				}
			}
		} catch(FileNotFoundException e) {
			//TODO: Archivo no encontrado :/
			
		} catch (IOException e) {
			Console.error("Couldn't read config file... Should be located in 'config/config.ini");
			
			if(developmentMode) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Espera a la entrada de texto (comandos)
	 */
	private static void waitForCommands() {
		//Quiero que siempre este escuchando..
		while(true) {
			//String command = in.nextLine();
			String[] ccommand = in.nextLine().split(" ");
			
			switch(ccommand[0]) {
				case "help":
					Console.out("List of available commands:\n");
					System.out.println("- help => shows this awesome list");
					System.out.println("- devmode => enable/disable development mode. Which includes information about packets and detailed error messages");
					break;
					
				case "devmode":
					if(!developmentMode) {
						developmentMode = true;
						Console.alert("DevelopmentMode Activated");
					} else {
						developmentMode = false;
						Console.alert("DevelopmentMode Desactivated.");
					}
					break;
					
				default:
					Console.out("Unknown command :/");
			}
		}
	}
}
