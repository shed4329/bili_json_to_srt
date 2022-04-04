import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;


public class toSrt extends Application{
	File json;
	File srt;
	public static void main(String[] args){
		launch();
	}
	public static void toSrt(File file,File srt)  throws Exception{
		System.out.println("Application Begin");
		//File file = new File("C:/Users/shed4/Videos/哔哩哔哩下载/80433022/137649199/en_us.json");	
		byte[] b = new byte[(int)file.length()];
		InputStream in= new FileInputStream(file);
		in.read(b);

		//File srt = new File("C:/Users/shed4/Videos/哔哩哔哩下载/80433022/137649199/en_us.srt");
		OutputStream out =new FileOutputStream(srt,true);
		

		int left = 0;
		int right = 0;
		String str = new String(b);
		System.out.println("file size:"+str.length()+"B");
		//System.out.println(str);
		int begin =0;
		int end =0;
		for(int i=0;i<100;i++){
			begin = str.indexOf("{",(begin+1));
			//System.out.println(begin);
			end=str.indexOf("}",(end+1));
			//System.out.println(end);
			if(begin==-1){
				System.out.println("finish");
				System.out.println("input File:"+file.getPath());
				System.out.println("output File:"+srt.getPath());
				System.out.println("Time:"+new java.util.Date(System.currentTimeMillis()));
				break;
			}
			String string = str.substring(begin,end);
			//System.out.println(string);

			int time0Begin = string.indexOf("m");
			int time0End = string.indexOf(",");
			String Time0 = string.substring((time0Begin+3),time0End);
			//System.out.println("Begin:"+Time0);  

			int time1Begin = string.indexOf("o",(time0Begin+1));
			int time1End = string.indexOf(",",(time0End+1));
			String Time1 = string.substring((time1Begin+3),time1End);
			//System.out.println("End:"+Time1);  
			
			int contentBegin = string.indexOf("e",time1Begin);
			int contentEnd = string.length()-1;
			String content = string.substring((contentBegin+6),contentEnd);
			//System.out.println("Content:"+content);
			
			String toWrite = String.valueOf(i)+"\n"+String.valueOf(Time0)+"-->"+String.valueOf(Time1)+"\n"+content+"\n"+"\n";
			System.out.println(toWrite);
			
			byte[] strByte=toWrite.getBytes();
      		for (int j = 0; j < strByte.length; j++) {
           		out.write(strByte[j]);
       		}
		}
		in.close();
		out.close();

	}
	public void start(Stage stage){
		Button jsonFile = new Button("json");
		Button srtFile = new Button("srt");
		Button toSrtFile = new Button("toSrt");
		
		FlowPane flowpane = new FlowPane(jsonFile,srtFile,toSrtFile);
		//flowpane.addAll(jsonFile,srtFile,toSrtFile);
		
		Scene scene = new Scene(flowpane);
		
		stage.setScene(scene);
		stage.setTitle("bilibili json to srt tool");
		stage.show();
		
		jsonFile.setOnAction((ActionEvent e) ->{
			FileChooser filechooser = new FileChooser();
			filechooser.setTitle("select json file");
			filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON","*.json"));
			json = filechooser.showOpenDialog(stage);
		});
		srtFile.setOnAction((ActionEvent e) ->{
			FileChooser filechooser = new FileChooser();
			filechooser.setTitle("select srt file");
			filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SRT","*.srt"));
			srt = filechooser.showSaveDialog(stage);
		});
		toSrtFile.setOnAction((ActionEvent e) ->{
			if(json!=null&&srt!=null){
				try{
					toSrt(json,srt);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
	}
}