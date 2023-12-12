package com.cens.tareas.appdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.springframework.stereotype.Component;


@Component
public class AppDataImpl  implements AppData {

	private String name; 

	private TreeMap<String,GeneralOption> generalOptions; 
	private ArrayList<GeneralOption> sortedGeneralOptions;
			
	public AppDataImpl() {
		name = "CENS gestion";		
		generalOptions = new TreeMap<>();
		sortedGeneralOptions = new ArrayList<>();
		int order=0;
		
		GeneralOption opTarea = new GeneralOption(order,"Historial","Historial de tareas","/tarea/list","Tareas");

opTarea.addScreen("LIST","ingrese tareas a realizar");
opTarea.addScreen("CREATE","Tarea (Create)");
opTarea.addScreen("UPDATE","Tarea (Update)");
opTarea.addScreen("VIEW","Tarea (View)");
opTarea.addScreen("VIEWIMG","Tarea - View Image");

opTarea.setEmptyMessage("No data");

generalOptions.put("TAREA",opTarea);
sortedGeneralOptions.add(opTarea);

order++;


		
		Collections.sort(sortedGeneralOptions);
		
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public TreeMap<String, GeneralOption> getGeneralOptions() {
		return generalOptions;
	}
	
	@Override
	public ArrayList<GeneralOption> getSortedGeneralOptions() {
		return sortedGeneralOptions;
	}
	
	@Override
	public boolean isMainScreen(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getMainScreenCode().equals(screenCode);
	}
	
	@Override
	public String getMainScreenName(String optionCode) {
		return generalOptions.get(optionCode).getMainScreenName();
	}
	
	@Override
	public String getMainScreenLink(String optionCode) {
		return generalOptions.get(optionCode).getLink();
	}
	
	@Override
	public String getScreenName(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getScreen(screenCode);
	}

	@Override
	public String getEmptyMessage(String optionCode) {
		return generalOptions.get(optionCode).getEmptyMessage();
	}
		
}

