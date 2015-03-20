//PWD
//Anton Koval

//http://www.java-forum.org/java-basics-anfaenger-themen/70279-sekunden-zaehlen.html
//http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds
//http://zetcode.com/tutorials/javagamestutorial/snake/package PDW;
//http://www.tutego.de/java/articles/Doppelpufferung-Java-Double-Buffering.html*
//http://www.youtube.com/watch?v=OjEjhxDt3Ug

package PDW;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.plaf.FileChooserUI;

public class BoardT extends JFrame implements ActionListener {

	/**
	 * 
	 */

	
	private static final long serialVersionUID = 1L;

	private BufferedImage offImg;
	private Graphics bufferedgraphics;
	
	
	
	ArrayList<PlayerT> players = new ArrayList<PlayerT>();  
	ArrayList<MapT> maps = new ArrayList<MapT>();  
	
	public boolean onevsone = true;
	public int hilfanzspieler = 0;
	public int statleiste = 25;

	public int anzgegner = 0;
	public int win = 0;
	private int level = 2;

	private int dotsize = 32;
	private int itemsize = 20;
	private int itemzahl = 0;
	private int zufallrichtug[] = new int[anzgegner];
	private int hilfzufallrichtug[] = new int[anzgegner];
	public Timer timer;
	// Bilder Tank

	public Image symbolgegner[] = new Image[anzgegner];


	public Image bildgameover;
	public Image inforand;
	public Image picpause;
	// Bilder
	// Schuss

	public Image gegnerschuss[] = new Image[anzgegner];
	

	private JPanel pnl = new JPanel();


	ImageIcon iitankup2 = new ImageIcon(this.getClass().getResource(
			"GegnerTankup0.png"));
	ImageIcon iitankdown2 = new ImageIcon(this.getClass().getResource(
			"GegnerTankdown0.png"));
	ImageIcon iitankleft2 = new ImageIcon(this.getClass().getResource(
			"GegnerTankleft0.png"));
	ImageIcon iitankright2 = new ImageIcon(this.getClass().getResource(
			"GegnerTankright0.png"));

	
	ImageIcon iigameover = new ImageIcon(this.getClass().getResource(
			"Gameover.png"));
	ImageIcon iiinforand = new ImageIcon(this.getClass().getResource(
			"InfoRand.png"));
	ImageIcon iisymbolgegner = new ImageIcon(this.getClass().getResource(
			"SymbolGegner.png"));

	ImageIcon iipause = new ImageIcon(this.getClass().getResource("pause.png"));


	public int gegerhilfcontakt[] = new int[anzgegner];
	public boolean gegnerup[] = new boolean[anzgegner];
	public boolean gegnerleft[] = new boolean[anzgegner];
	public boolean gegnerright[] = new boolean[anzgegner];
	public boolean gegnerdown[] = new boolean[anzgegner];

	public boolean hinrechts[] = new boolean[anzgegner];
	public boolean hinlinks[] = new boolean[anzgegner];
	public boolean hinoben[] = new boolean[anzgegner];
	public boolean hinunten[] = new boolean[anzgegner];

	public boolean upschuss = false;
	public boolean downschuss = false;
	public boolean rightschuss = false;
	public boolean leftschuss = false;
	
	public boolean gameover = false;
	public boolean ingame = false;
	public boolean replay = false;
	public boolean p1win = false;
	public boolean p2win = false;
	public boolean pause = true;
	

	private boolean aufeis = false;
	
	
	public boolean gamestarted = false;

	public boolean ggifschuss[] = new boolean[anzgegner];
	public boolean gghelpifschussup[] = new boolean[anzgegner];
	public boolean gghelpifschussdown[] = new boolean[anzgegner];
	public boolean gghelpifschussleft[] = new boolean[anzgegner];
	public boolean gghelpifschussright[] = new boolean[anzgegner];

	public int ggspeed = 4;
	// für zeit
	public long zstVorher;
	public long zstNachher;
	// items


	private int nirvana = -1000;

	private int schussspeed = 20;
	private int rand = 832;


	private int gegnerschussx[] = new int[anzgegner];
	private int gegnerschussy[] = new int[anzgegner];
	private int ggschusszahl[] = new int[anzgegner];
	private int hilfproofnirvana[] = new int[anzgegner];

	private int ggspawnhilf = 0;
	private int symbolgegnerx[] = new int[anzgegner];
	private int symbolgegnery[] = new int[anzgegner];


	private int gegnerfeld[] = new int[anzgegner];
	private int delay = 8;
	private TAdapter kl = new TAdapter();// KeyListener
	// Hindernisse
	

	ArrayList<HindernissT> ziegelbloecke = new ArrayList<HindernissT>(); 
	ArrayList<HindernissT> stahlbloecke = new ArrayList<HindernissT>(); 
	ArrayList<HindernissT> flussbloecke = new ArrayList<HindernissT>(); 
	ArrayList<HindernissT> eisbloecke = new ArrayList<HindernissT>(); 
	ArrayList<HindernissT> buschbloecke = new ArrayList<HindernissT>(); 
	
	ArrayList<StuetzpunktT> stuetzpunkte = new ArrayList<StuetzpunktT>(); 
	


	public ItemT Item_hartemun;
	public ItemT Item_schiff;
	public ItemT Item_schnell;
	public ItemT Item_leben;

	public GegnerT[] gegner;

	public JButton btn_onevsgegner = new JButton("1 VS GEGNER");
	public JButton btn_twovsgegner = new JButton("2 VS GEGNER");
	public JButton btn_onevsone = new JButton("1 VS 1");
	public JButton btn_choose_map = new JButton("Spielfeld auswählen");
	public JButton btn_beenden = new JButton("Beenden");
	

	public int mode = 0;

	public JFrame frm_fehler = new JFrame("Fehler!");
	{
		frm_fehler.setSize(180, 120);
		frm_fehler.setResizable(false);
		frm_fehler.setLocationRelativeTo(null);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BoardT() {
		super("Panzer Division Wodka");
		this.add(pnl);

		; // damit die Butten verschoben werden können

		frm_fehler.setVisible(false);

	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(rand + 200, rand + statleiste);
		setLocationRelativeTo(null);

		setResizable(false);
		setVisible(true);
		this.pnl.setBackground(Color.black);

		// verschiedebe Mods
		addKeyListener(kl);
		// Timer
		timer = new Timer(delay, this);
		timer.start();


		offImg = (BufferedImage) createImage(rand + 200, rand + statleiste);
		bufferedgraphics = offImg.getGraphics();

	}

	// Initialisierung 1

	public void init() throws IOException {

		setFocusable(true);
		
		initMap();
		
		initGame(); // fehler
		setzeItem();
		initGegner();
		
		initSpieler();
		initSchuss();
		
		initGegnerSchuss();
		initGegnerhilfcontakt();
		//initLeben();
		this.setFocusable(true);
	}

	public void soundSchuss() throws UnsupportedAudioFileException,
			IOException, LineUnavailableException {

		// AudioInputStream myInputStream = AudioSystem.getAudioInputStream(new
		// File("shot.wav"));

		AudioInputStream myInputStream = AudioSystem.getAudioInputStream(this
				.getClass().getResource("shot.wav"));
		AudioFormat myAudioFormat = myInputStream.getFormat();
		int groesse = (int) (myAudioFormat.getFrameSize() * myInputStream
				.getFrameLength());
		byte[] mySound = new byte[groesse];
		DataLine.Info myInfo = new DataLine.Info(Clip.class, myAudioFormat,
				groesse);
		myInputStream.read(mySound, 0, groesse);

		Clip myClip = (Clip) AudioSystem.getLine(myInfo);
		myClip.open(myAudioFormat, mySound, 0, groesse);
		myClip.start();

	}

	public void soundFahr() throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {

		AudioInputStream myInputStream = AudioSystem.getAudioInputStream(this
				.getClass().getResource("fahr.wav"));
		AudioFormat myAudioFormat = myInputStream.getFormat();
		int groesse = (int) (myAudioFormat.getFrameSize() * myInputStream
				.getFrameLength());
		byte[] mySound = new byte[groesse];
		DataLine.Info myInfo = new DataLine.Info(Clip.class, myAudioFormat,
				groesse);
		myInputStream.read(mySound, 0, groesse);

		Clip myClip = (Clip) AudioSystem.getLine(myInfo);
		myClip.open(myAudioFormat, mySound, 0, groesse);
		myClip.start();

	}

	public void ingameinitSchuss(int spieler) {

		
		if ((players.get(spieler).helpifschussup == false)
				&& (players.get(spieler).helpifschussdown == false)
				&& (players.get(spieler).helpifschussright == false)
				&& (players.get(spieler).helpifschussleft == false)) {

			players.get(spieler).schussx = players.get(spieler).getX() + 23;
			players.get(spieler).schussy = players.get(spieler).getY() + 23;

		}

	}

	public void initSchuss() {

		for (int i = 0; i < players.size(); i++) {

			if ((players.get(i).helpifschussup == false)
					&& (players.get(i).helpifschussdown == false)
					&& (players.get(i).helpifschussright == false)
					&& (players.get(i).helpifschussleft == false)) {

				players.get(i).schussx = players.get(i).getX() + 23;
				players.get(i).schussy = players.get(i).getY() + 23;

			}

		}

	}

	public void ingameinitGegnerSchuss(int zgegner) {

		if ((gghelpifschussup[zgegner] == false)
				&& (gghelpifschussdown[zgegner] == false)
				&& (gghelpifschussright[zgegner] == false)
				&& (gghelpifschussleft[zgegner] == false)) {

			gegnerschussx[zgegner] = gegner[zgegner].x + 23;
			gegnerschussy[zgegner] = gegner[zgegner].y + 23;

		}

	}

	public void initGegnerSchuss() {

		for (int i = 0; i < anzgegner; i++) {

			if ((gghelpifschussup[i] == false)
					&& (gghelpifschussdown[i] == false)
					&& (gghelpifschussright[i] == false)
					&& (gghelpifschussleft[i] == false)) {

				gegnerschussx[i] = gegner[i].x + 23;
				gegnerschussy[i] = gegner[i].y + 23;
			}

		}

	}

	public void ingameinitSpieler(int spieler) {
//
		players.get(spieler).setX(players.get(spieler).getMap_x());
		players.get(spieler).setY(players.get(spieler).getMap_y());
		
		if(spieler == 0){
			
			players.get(spieler).uphilf = true;
		}
		else{
			players.get(spieler).downhilf = true;
		}
//
//		if ((onevsone == true) && (spieler == 1)) {
//			players.get(1).downhilf = true;
//			players.get(1).uphilf = false;
//		}
//

	}

	

	public void initSpieler() { //VIELLEICH NICHT NUR INIT sondern immer

		for (int i = 0; i < players.size(); i++) {

			players.get(i).leben = 3;
			players.get(i).kills = 0;
			
			if (gamestarted == false) {
				players.get(i).anzahlwin = 0;
				
			}


		}
		gamestarted = true;

//		
	

	}

	public void initZeit() {

		zstVorher = System.currentTimeMillis();
	}

	// Initialisierung 2
	public void initGame() {
		ingame = true;
	
		
//		for (int i = 0; i < players.size(); i++) {
//			symbolgegner[i] = iisymbolgegner.getImage();
//		}

		

		bildgameover = iigameover.getImage();
		inforand = iiinforand.getImage();
		picpause = iipause.getImage();



//		for (int i = 0; i < anzgegner; i++) { TODO
//
//			gegnerschuss[i] = iischuss.getImage();
//		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).schiff = false;
			players.get(i).hartemun = false;
			players.get(i).schnell = false;
			players.get(i).schnell = false;
			players.get(i).sek = 0;
		}
		// players.size() = menu.players.size();




		gegner = new GegnerT[anzgegner];

	}
	

	public void setzeItem() {

		do {
			itemzahl = (int) (Math.random() * 100);

		} while ((itemzahl != 1) && (itemzahl != 2) && (itemzahl != 3)
				&& (itemzahl != 4));

		Item_hartemun = new ItemT("HarteMun.png", 1);
		Item_schiff = new ItemT("Schiff.png", 2);
		Item_schnell = new ItemT("Schnell.png", 3);
		Item_leben = new ItemT("Item_Leben.png", 4);
		if (itemzahl == 1) {

			Item_hartemun.setzeItem(rand / dotsize + 1, rand / dotsize + 1,
					itemsize);
		}
		if (itemzahl == 2) {

			Item_schiff.setzeItem(rand / dotsize + 1, rand / dotsize + 1,
					itemsize);
		}
		if (itemzahl == 3) {

			Item_schnell.setzeItem(rand / dotsize + 1, rand / dotsize + 1,
					itemsize);
		}
		if (itemzahl == 4) {

			Item_leben.setzeItem(rand / dotsize + 1, rand / dotsize + 1,
					itemsize);
		}

	}

	

	public void initGegnerhilfcontakt() {
		for (int i = 0; i < anzgegner; i++) {

			gegerhilfcontakt[i] = 100;

		}

	}

	public void initGegner() {

		if (anzgegner > 13) {

			anzgegner = 13;
		}

		for (int i = 0; i < anzgegner; i++) {

			gegner[i] = new GegnerT("GegnerTankdown0.png", 1);

		}

		if ((level == 1) || (level == 2) || (level == 3)) {
			for (int i = 0; i < anzgegner; i++) {
				if (onevsone == false) {
					if (i < 3) {
						gegner[i].x = 10 + i * 382;
						gegner[i].y = statleiste + 10;
						gegnerfeld[i] = 1;

					} else {
						gegner[i].x = nirvana;
						gegner[i].y = nirvana;
						gegnerfeld[i] = 2;
					}

					gghelpifschussdown[i] = false;
					gghelpifschussup[i] = false;
					gghelpifschussleft[i] = false;
					gghelpifschussright[i] = false;

					symbolgegnerx[i] = 832 + 20;
					symbolgegnery[i] = 60 * i + 35;
					

				}
			}
		}
	}

	
	ActionListener start = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// BoardT.this.repaint();

		}
	};

	

	public void replay() throws IOException {

		if (ingame == false){
			if (replay == true) {
	
				p2win = false;
				p1win = false;
				try {
					init();
				} finally {
	
					// ut.println("Fehler");
					// frm_fehler.setVisible(true);
				}
				
				replay = false;
	
				
				
			}
		}

	}

	public void actionPerformed(ActionEvent e) {

		moveschuss();
		repaint();

	}
	public void chooseMap() 
	{
		System.out.println();
		ArrayList<String> map_path_list = new ArrayList<String>();  //TODO
							
		File path = new File(getJarExecutionDirectory()+"/maps");
		
	    File [] maps = path.listFiles();
	    for (int i = 0; i < maps.length; i++){
	        if (maps[i].isFile()){ //this line weeds out other directories/folders

	        	map_path_list.add(maps[i].toString());
	            
	        }
	        
	        
	    }
	    
	    for(int i = 0; i < map_path_list.size(); i++){
	    	
	    	//String[] splittedcontent = map_path_list[i].split(Pattern.quote(","));
	    	
	    }
        

//		//In response to a button click:
//		//int returnVal = fc.showOpenDialog(aComponent);
//	    
//	        int returnVal = fc.showOpenDialog();
//
//	        if (returnVal == JFileChooser.APPROVE_OPTION) {
//	            File file = fc.getSelectedFile();
//	            //This is where a real application would open the file.
//	            log.append("Opening: " + file.getName() + "." + newline);
//	        } else {
//	            log.append("Open command cancelled by user." + newline);
//	        }
	}
	
	//Quelle: http://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
	//gibt den aktuellenpfad
	public static String getJarExecutionDirectory()
	  {
	    String jarFile = null;
	    String jarDirectory = null;
	    int cutFileSeperator = 0;
	    int cutSemicolon = -1;
	 
	    jarFile = System.getProperty("java.class.path");
	    // Cut seperators
	    cutFileSeperator = jarFile.lastIndexOf(System.getProperty("file.separator"));
	    jarDirectory = jarFile.substring(0, cutFileSeperator);
	    // Cut semicolons
	    cutSemicolon = jarDirectory.lastIndexOf(';');
	    jarDirectory = jarDirectory.substring(cutSemicolon+1, jarDirectory.length());
	 
	    return jarDirectory+System.getProperty("file.separator");
	  }
	 
	 
	public void initMap() throws IOException{
		
		FileReader fr = new FileReader("-map1.txt");
		BufferedReader br = new BufferedReader(fr);
		FileReader cfr = new FileReader("-map1.txt");
		BufferedReader cbr = new BufferedReader(cfr);
		String content = "";
		int count = 0;
		int mapstructure[] = null;
		while(cbr.readLine() != null){
			
			count++;
		}
		
		for(int i = 0;i < count;i++){
			mapstructure = new int[26*26];  
			String linecontent = br.readLine();
			content = content + linecontent;

		}
		String[] splittedcontent = content.split(Pattern.quote(","));
		
		for (int i = 0; i < splittedcontent.length; i++) {
		    try {
		    	mapstructure[i] = Integer.parseInt(splittedcontent[i]);
		    } catch (NumberFormatException nfe) {};
		    
		}
		
		MapT map = new MapT(mapstructure);
		maps.add(map);
		buildMap(map);
		br.close();
		cbr.close();
		
	}
	public void buildMap(MapT map){
    	
		ziegelbloecke.clear();
		ziegelbloecke.clear();
		stahlbloecke.clear();
		flussbloecke.clear();
		eisbloecke.clear();
		buschbloecke.clear();
		stuetzpunkte.clear();
		players.clear();
		
    	for(int i = 0; i < map.mapstucture.length; i++)
    	{
    		chargeMap(i, map);
    		
    		//Hindernisse
    		if(map.mapstucture[i] == 1){
    			HindernissT ziegelblock = new HindernissT("Ziegel.png",1,map.map_x,map.map_y);
    			ziegelbloecke.add(ziegelblock);

    		}
    		if(map.mapstucture[i] == 2){
    			HindernissT stahlblock = new HindernissT("Stahlwand.png",2,map.map_x,map.map_y);
    			stahlbloecke.add(stahlblock);


    		}
    		if(map.mapstucture[i] == 3){
    			HindernissT flussblock = new HindernissT("Fluss.png",3,map.map_x,map.map_y);
    			flussbloecke.add(flussblock);	
    			
    		}
    		if(map.mapstucture[i] == 4){
    			HindernissT eisblock = new HindernissT("Eis.png",4,map.map_x,map.map_y);
    			eisbloecke.add(eisblock);
  
    		}
    		
    		if(map.mapstucture[i] == 5){
    			HindernissT buschblock = new HindernissT("Busch.png",5,map.map_x,map.map_y);
    			buschbloecke.add(buschblock);
    	
    		}

    	}
    	map.map_x = 0;
		map.map_y = 0;
		
    	for(int i = 0; i < map.mapstucture.length; i++)
    	{
    		chargeMap(i, map);
    		if(map.mapstucture[i] == 11)
    		{
    			StuetzpunktT stp1 = new StuetzpunktT("Stuetzpunkt.png",11,map.map_x,map.map_y);
    			stuetzpunkte.add(stp1);
    		}
    	}	
    	map.map_x = 0;
		map.map_y = 0;
		
    	for(int i = 0; i < map.mapstucture.length; i++)
    	{
    		chargeMap(i, map);
    		if(map.mapstucture[i] == 12){
    			StuetzpunktT stp2 = new StuetzpunktT("Stuetzpunkt2.png",12,map.map_x,map.map_y);
    			stuetzpunkte.add(stp2);
    		}
    		
        }
    	//Stuetzpunkte
    	map.map_x = 0;
		map.map_y = 0;
    	for(int i = 0; i < map.mapstucture.length; i++) //TODO
    	{
    		chargeMap(i, map);
    		if(map.mapstucture[i] == 101){
    			PlayerT player1 = new PlayerT(1,map.map_x,map.map_y);
    			players.add(player1);
    			players.get(0).setMap_x(map.map_x);
    			players.get(0).setMap_y(map.map_y);
    		}
    	
    	}
    	map.map_x = 0;
		map.map_y = 0;
    	for(int i = 0; i < map.mapstucture.length; i++)
    	{
    		chargeMap(i, map);
    		if(map.mapstucture[i] == 102){
    			PlayerT player2 = new PlayerT(2,map.map_x,map.map_y);
    			players.add(player2);
    			players.get(1).setMap_x(map.map_x);
    			players.get(1).setMap_y(map.map_y);
    		}
    		
    	}
    	map.map_x = 0;
		map.map_y = 0;
    }

	private void chargeMap(int i,MapT map) {
		map.map_x += map.block_width;
		
		if(i % map.map_width == 0)
		{
			if(i == 0){
				map.map_y += statleiste;
				map.map_x = 1;
			}
			else
			{
				map.map_y += map.block_width;
				map.map_x = 1;
			}
		}
	}
	// paint --> Alles graphische
	public void paint(Graphics g) {
		
		//System.out.println("PL1: "+ p1win +"| PL2: "+p2win +"| ingame: "+ ingame);
		
		contaktGegnerschuss();
		// bufferedgraphics.clearRect(0, 0, rand+200, rand+statleiste);
		// bufferedgraphics.setColor(Color.white);
		bufferedgraphics.fillRect(0, 0, rand + 200, rand + statleiste);
		prooflevel();
		proofgegner();
		spawnGegner();
		contaktitem(); //contankfluss ist dort.
		contaktrand();
		contaktGegner();
		contaktbusch();
		contaktziegel();
		contaktstahlwand();
		contakteis();
		contaktMitspielerschuss();
		moveGegnerschuss();
		moveGegner();
		contaktRandgegner();
		contaktStuetzpunktgegner();
		contaktStahlwandgegner();
		contaktFlussgegner();
		contaktZiegelgegner();
		contaktSpielergegner();
		obSchussDesGegner();
		schussDesGegner();
		contaktGegnerHindernissschuss();
		contaktMitspieler();
		contaktSpielerSchussVonGegner();
		contaktStuetzpunktschuss();
		contaktStuetzpunktGegnerschuss();
		proofgameover();
	//	proofwin(); timer stoppt bei 0 gegenern
		contaktStuetzpunkt();
		spawnitem();
		schuss();

		bufferedgraphics.drawImage(inforand, rand, statleiste, this);
		bufferedgraphics.setFont(new Font("Arial Black", Font.PLAIN, 19));
		if (players.size() >= 1)
		{	
		bufferedgraphics.drawString("PL1 |win:" + players.get(0).anzahlwin + "",
				(rand + 67), 60 + statleiste);
		}

		if (players.size() >= 2) {
			bufferedgraphics.drawString("PL2 |win:" + players.get(1).anzahlwin + "",
					(rand + 67), 180 + statleiste);
		}

		bufferedgraphics.setFont(new Font("Times New Roman", Font.BOLD, 14));
		bufferedgraphics.drawString("© Anton Koval", (rand + 90), rand
				+ statleiste - 10);
		if (onevsone == true) {
			String str = "";
			if (p1win) {
				str = "PL1 WIN";
			}
			else if (p2win) {
				str = "PL2 WIN";
			}
			else{
				str = "";
			}
			
			bufferedgraphics.setFont(new Font("Arial Black", Font.PLAIN, 24));
			bufferedgraphics.drawString(str, (rand + 67), 320 + statleiste);

		}
		String str = "";
		if (ingame == true) {
			
			str = "";
		}	
		else{
			str = "Rückspiel? Taste: 'R'!";
		}
			
			
			bufferedgraphics.setFont(new Font("Arial Black", Font.PLAIN, 14));
			bufferedgraphics.drawString(str, (rand + 15), 360 + statleiste);

		
		
		// /// Baustelle wegen replay

		// !!!!!!!!!!!
		
			if(players.size() >= 1){
				for (int i = 0; i < players.get(0).leben; i++) { 
					bufferedgraphics.drawImage(players.get(0).Picleben, (rand + 67) + i * 40,
							70 + statleiste, this);
				}
			}
			if(players.size() >= 2){
				for (int i = 0; i < players.get(1).leben; i++) { 
					bufferedgraphics.drawImage(players.get(1).Picleben, (rand + 67) + i * 40,
						190 + statleiste, this);
				}
			}
		
		for (int i = 0; i < anzgegner; i++) {
			bufferedgraphics.drawImage(symbolgegner[i], symbolgegnerx[i],
					statleiste + symbolgegnery[i], this);

		}

		// Hindernisse Paint
		for (int i = 0; i < ziegelbloecke.size(); i++) {
			bufferedgraphics.drawImage(ziegelbloecke.get(i).Hinderniss,ziegelbloecke.get(i).getX(),
					ziegelbloecke.get(i).getY(), this);
		}
		
		for (int i = 0; i < stahlbloecke.size(); i++) {
			bufferedgraphics.drawImage(stahlbloecke.get(i).Hinderniss, stahlbloecke.get(i).getX(),
					stahlbloecke.get(i).getY(), this);
		}
		for (int i = 0; i < flussbloecke.size(); i++) {
			bufferedgraphics.drawImage(flussbloecke.get(i).Hinderniss, flussbloecke.get(i).getX(),
					flussbloecke.get(i).getY(), this);
		}
			
		for (int i = 0; i < eisbloecke.size(); i++) {
		bufferedgraphics.drawImage(eisbloecke.get(i).Hinderniss, eisbloecke.get(i).getX(),
				eisbloecke.get(i).getY(), this);
		}


		for (int i = 0; i < players.size(); i++) {

			if ((players.get(i).up) || (players.get(i).uphilf)) {

				bufferedgraphics.drawImage(players.get(i).Schuss, players.get(i).schussx, players.get(i).schussy,
						this);
				bufferedgraphics.drawImage(players.get(i).Tankup, players.get(i).getX(), players.get(i).getY(), this);

				players.get(i).uphilf = true;
				players.get(i).downhilf = false;
				players.get(i).lefthilf = false;
				players.get(i).righthilf = false;
			}

			if ((players.get(i).down) || (players.get(i).downhilf)) {

				bufferedgraphics.drawImage(players.get(i).Schuss, players.get(i).schussx, players.get(i).schussy,
						this);
				bufferedgraphics.drawImage(players.get(i).Tankdown, players.get(i).getX(), players.get(i).getY(),
						this);

				players.get(i).downhilf = true;
				players.get(i).uphilf = false;
				players.get(i).lefthilf = false;
				players.get(i).righthilf = false;

			}

			if ((players.get(i).left) || ( players.get(i).lefthilf)) {

				bufferedgraphics.drawImage(players.get(i).Schuss, players.get(i).schussx, players.get(i).schussy,
						this);
				bufferedgraphics.drawImage(players.get(i).Tankleft, players.get(i).getX(), players.get(i).getY(),
						this);

				players.get(i).lefthilf = true;
				players.get(i).righthilf = false;
				players.get(i).downhilf = false;
				players.get(i).uphilf = false;
			}
			if ((players.get(i).right) || (players.get(i).righthilf)) {

				bufferedgraphics.drawImage(players.get(i).Schuss, players.get(i).schussx, players.get(i).schussy,
						this);
				bufferedgraphics.drawImage(players.get(i).Tankright, players.get(i).getX(), players.get(i).getY(),
						this);

				players.get(i).righthilf = true;
				players.get(i).uphilf = false;
				players.get(i).downhilf = false;
				players.get(i).lefthilf = false;
			}

		}

		// Busch (Panzer wird vom Spieler nicht gesehen)
		for (int i = 0; i < anzgegner; i++) {
			bufferedgraphics.drawImage(gegnerschuss[i], gegnerschussx[i],
					gegnerschussy[i], this);
			bufferedgraphics.drawImage(gegner[i].Gegner, gegner[i].x, gegner[i].y, this);

		}

		for (int i = 0; i < buschbloecke.size(); i++) {
			bufferedgraphics.drawImage(buschbloecke.get(i).Hinderniss, buschbloecke.get(i).getX(),
					buschbloecke.get(i).getY(), this);

		}

		if (itemzahl == 1) {
			bufferedgraphics.drawImage(Item_hartemun.Item, Item_hartemun.x,
					Item_hartemun.y, this);
		} else if (itemzahl == 2) {
			bufferedgraphics.drawImage(Item_schiff.Item, Item_schiff.x,
					Item_schiff.y, this);
		} else if (itemzahl == 3) {
			bufferedgraphics.drawImage(Item_schnell.Item, Item_schnell.x,
					Item_schnell.y, this);
		} else if (itemzahl == 4) {
			bufferedgraphics.drawImage(Item_leben.Item, Item_leben.x,
					Item_leben.y, this);
		} else {

		}
		
		for(int i=0;i < stuetzpunkte.size();i++){
			bufferedgraphics.drawImage(stuetzpunkte.get(i).Stuetzpunkt, stuetzpunkte.get(i).getX(),
					stuetzpunkte.get(i).getY(), this);
		}
		
		

		if (gameover == true) {
			//bufferedgraphics.drawImage(bildgameover, 0, 0, this);
		}

		if (pause == true) {
			// bufferedgraphics.drawImage(picpause, rand/2-135,
			// rand/2-135+statleiste,this);

		}

		g.drawImage(offImg, 0, 0, this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();

	}

	public void spawnitem() {
		// Lässt Item wo andes spawnen (zufällig)
		int zahl = (int) (Math.random() * 10000);

		if (zahl == 1) {
			setzeItem();
		}

	}

	public void contaktitem() {
		for (int j = 0; j < players.size(); j++) {

		
			contaktHindernissschuss(j);
			move(j);
			contaktfluss(j); //ACHTUNG!! MOVE IMMER VONN CONTAKT/COLLISION

			

			
			if ((players.get(j).schiff == true) || (players.get(j).hartemun == true)
					|| (players.get(j).schnell == true)) {
				if (players.get(j).hilfinit != true) {
					initZeit();
				}
				players.get(j).hilfinit = true;
				zstNachher = System.currentTimeMillis();
				players.get(j).sek = (int) ((zstNachher - zstVorher) / 1000);

			}

			if (players.get(j).sek == 20) {

				players.get(j).schiff = false;
				players.get(j).hartemun = false;
				players.get(j).schnell = false;
				players.get(j).sek = 0;
				players.get(j).hilfinit = false;
			}

			switch (itemzahl) {

			case 1:
				for (int i = 0; i < players.size(); i++) {

					if ((players.get(i).getX() >= Item_hartemun.x - 50)
							&& (players.get(i).getX() <= Item_hartemun.x + 20)
							&& (players.get(i).getY() <= (Item_hartemun.y + 20))
							&& (players.get(i).getY() > (Item_hartemun.y - 50) && (players.get(i).right))) {
						Item_hartemun.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).hartemun = true;
						players.get(j).schiff = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;
					}
					if ((players.get(i).getX() <= Item_hartemun.x + 20)
							&& (players.get(i).getX() >= Item_hartemun.x - 50)
							&& (players.get(i).getY() <= Item_hartemun.y + 20)
							&& (players.get(i).getY() > (Item_hartemun.y - 50) && (players.get(i).left))) {
						Item_hartemun.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).hartemun = true;
						players.get(j).schiff = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;
					}

					if ((players.get(i).getY() <= Item_hartemun.y + 20)
							&& (players.get(i).getX() >= Item_hartemun.x - 50)
							&& (players.get(i).getX() <= Item_hartemun.x + 20)
							&& (players.get(i).getY() > (Item_hartemun.y - 50) && (players.get(i).up))) {
						Item_hartemun.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).hartemun = true;
						players.get(j).schiff = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;
					}

					if ((players.get(i).getY() >= Item_hartemun.y - 50)
							&& (players.get(i).getX() >= Item_hartemun.x - 50)
							&& (players.get(i).getX() <= Item_hartemun.x + 20)
							&& (players.get(i).getY() < (Item_hartemun.y + 20)) && (players.get(i).down)) {
						Item_hartemun.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).hartemun = true;
						players.get(j).schiff = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;
					}
				}
				break;
			case 2:
				for (int i = 0; i < players.size(); i++) {

					if ((players.get(i).getX() >= Item_schiff.x - 50)
							&& (players.get(i).getX() <= Item_schiff.x + 20)
							&& (players.get(i).getY() <= (Item_schiff.y + 20))
							&& (players.get(i).getY() > (Item_schiff.y - 50) && (players.get(i).right))) {
						Item_schiff.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = true;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;

					}
					if ((players.get(i).getX() <= Item_schiff.x + 20)
							&& (players.get(i).getX() >= Item_schiff.x - 50)
							&& (players.get(i).getY() <= Item_schiff.y + 20)
							&& (players.get(i).getY() > (Item_schiff.y - 50) && (players.get(i).left))) {
						Item_schiff.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = true;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;

					}

					if ((players.get(i).getY() <= Item_schiff.y + 20)
							&& (players.get(i).getX() >= Item_schiff.x - 50)
							&& (players.get(i).getX() <= Item_schiff.x + 20)
							&& (players.get(i).getY() > (Item_schiff.y - 50) && (players.get(i).up))) {
						Item_schiff.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = true;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;

					}

					if ((players.get(i).getY() >= Item_schiff.y - 50)
							&& (players.get(i).getX() >= Item_schiff.x - 50)
							&& (players.get(i).getX() <= Item_schiff.x + 20)
							&& (players.get(i).getY() < (Item_schiff.y + 20)) && (players.get(i).down)) {
						Item_schiff.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = true;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;
					}

				}
				break;
			case 3:
				for (int i = 0; i < players.size(); i++) {

					if ((players.get(i).getX() >= Item_schnell.x - 50)
							&& (players.get(i).getX() <= Item_schnell.x + 20)
							&& (players.get(i).getY() <= (Item_schnell.y + 20))
							&& (players.get(i).getY() > (Item_schnell.y - 50) && (players.get(i).right))) {
						Item_schnell.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = true;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;

					}
					if ((players.get(i).getX() <= Item_schnell.x + 20)
							&& (players.get(i).getX() >= Item_schnell.x - 50)
							&& (players.get(i).getY() <= Item_schnell.y + 20)
							&& (players.get(i).getY() > (Item_schnell.y - 50) && (players.get(i).left))) {
						Item_schnell.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = true;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;

					}

					if ((players.get(i).getY() <= Item_schnell.y + 20)
							&& (players.get(i).getX() >= Item_schnell.x - 50)
							&& (players.get(i).getX() <= Item_schnell.x + 20)
							&& (players.get(i).getY() > (Item_schnell.y - 50) && (players.get(i).up))) {
						Item_schnell.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = true;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;

					}

					if ((players.get(i).getY() >= Item_schnell.y - 50)
							&& (players.get(i).getX() >= Item_schnell.x - 50)
							&& (players.get(i).getX() <= Item_schnell.x + 20)
							&& (players.get(i).getY() < (Item_schnell.y + 20)) && (players.get(i).down)) {
						Item_schnell.setzeItem(rand / dotsize + 1, rand
								/ dotsize + 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = true;

						players.get(j).sek = 0;
						players.get(j).hilfinit = false;
					}

				}
			case 4:
				for (int i = 0; i < players.size(); i++) {

					if ((players.get(i).getX() >= Item_leben.x - 50)
							&& (players.get(i).getX() <= Item_leben.x + 20)
							&& (players.get(i).getY() <= (Item_leben.y + 20))
							&& (players.get(i).getY() > (Item_leben.y - 50) && (players.get(i).right))) {
						Item_leben.setzeItem(rand / dotsize + 1, rand / dotsize
								+ 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;
						players.get(j).item_leben = true;
						players.get(j).hilfinit = false;
						plusLeben(i);

					}
					if ((players.get(i).getX() <= Item_leben.x + 20)
							&& (players.get(i).getX() >= Item_leben.x - 50)
							&& (players.get(i).getY() <= Item_leben.y + 20)
							&& (players.get(i).getY() > (Item_leben.y - 50) && (players.get(i).left))) {
						Item_leben.setzeItem(rand / dotsize + 1, rand / dotsize
								+ 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;
						players.get(j).item_leben = true;
						players.get(j).hilfinit = false;
						plusLeben(i);

					}

					if ((players.get(i).getY() <= Item_leben.y + 20)
							&& (players.get(i).getX() >= Item_leben.x - 50)
							&& (players.get(i).getX() <= Item_leben.x + 20)
							&& (players.get(i).getY() > (Item_leben.y - 50) && (players.get(i).up))) {
						Item_leben.setzeItem(rand / dotsize + 1, rand / dotsize
								+ 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;
						players.get(j).item_leben = true;
						players.get(j).hilfinit = false;
						plusLeben(i);

					}

					if ((players.get(i).getY() >= Item_leben.y - 50)
							&& (players.get(i).getX() >= Item_leben.x - 50)
							&& (players.get(i).getX() <= Item_leben.x + 20)
							&& (players.get(i).getY() < (Item_leben.y + 20)) && (players.get(i).down)) {
						Item_leben.setzeItem(rand / dotsize + 1, rand / dotsize
								+ 1, itemsize);
						setzeItem();
						players.get(j).schiff = false;
						players.get(j).hartemun = false;
						players.get(j).schnell = false;
						players.get(j).item_leben = true;
						players.get(j).hilfinit = false;
						plusLeben(i);
					}

				}
				break;

			}

		}
	}

	public void plusLeben(int spieler) {

		if ((players.get(spieler).leben < 3) && (players.get(spieler).item_leben == true)) {

			players.get(spieler).leben = players.get(spieler).leben + 1;
			players.get(spieler).item_leben = false;
		}

	}

	// Wenn gegen Wand, dann speed negativieren == Stehen
	public void contaktrand() {
		for (int i = 0; i < players.size(); i++) {

			if ((players.get(i).getX() >= rand - 50) && (players.get(i).right)) {
				
				players.get(i).setX(players.get(i).getX() - players.get(i).speed);

				ingameinitSchuss(i);

			}
			else if ((players.get(i).getX() <= 0) && (players.get(i).left)) {
				players.get(i).setX(players.get(i).getX() + players.get(i).speed);

				ingameinitSchuss(i);

			}
			else if ((players.get(i).getY() >= rand + statleiste - 50) && (players.get(i).down)) {
				players.get(i).setY(players.get(i).getY() - players.get(i).speed);

				ingameinitSchuss(i);

			} 
			else if ((players.get(i).getY() <= statleiste) && (players.get(i).up)) {
				players.get(i).setY(players.get(i).getY() + players.get(i).speed);

				ingameinitSchuss(i);
				

			}

		}

	}

	public void contaktSpielergegner() {
		for (int j = 0; j < players.size(); j++) {
			for (int i = 0; i < anzgegner; i++) {

				if ((gegner[i].x >= players.get(j).getX()- 50)
						&& (gegner[i].x <= players.get(j).getX()+ 50)
						&& (gegner[i].y <= (players.get(j).getY()+ 50))
						&& (gegner[i].y >= (players.get(j).getY()- 50) && (gegnerright[i]))) {
					gegner[i].x = gegner[i].x - ggspeed;

					gegerhilfcontakt[i] = 1;
					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].x <= players.get(j).getX()+ 50)
						&& (gegner[i].x >= players.get(j).getX()- 50)
						&& (gegner[i].y <= players.get(j).getY()+ 50)
						&& (gegner[i].y >= (players.get(j).getY()- 50) && (gegnerleft[i]))) {
					gegner[i].x = gegner[i].x + ggspeed;

					gegerhilfcontakt[i] = 1;
					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y <= players.get(j).getY()+ 50)
						&& (gegner[i].x >= players.get(j).getX()- 50)
						&& (gegner[i].x <= players.get(j).getX()+ 50)
						&& (gegner[i].y >= (players.get(j).getY()- 50) && (gegnerright[i]))) {
					gegner[i].y = gegner[i].y + ggspeed;

					gegerhilfcontakt[i] = 1;
					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y >= players.get(j).getY()- 50)
						&& (gegner[i].x >= players.get(j).getX()- 50)
						&& (gegner[i].x <= players.get(j).getX()+ 50)
						&& (gegner[i].y <= (players.get(j).getY()+ 50)) && (gegnerdown[i])) {
					gegner[i].y = gegner[i].y - ggspeed;

					gegerhilfcontakt[i] = 1;
					ingameinitGegnerSchuss(i);

				}
				if (gegerhilfcontakt[i] == 1) {

					initGegnerhilfcontakt();

				}

			}
		}
	}

	public void contaktZiegelgegner() {
		for (int j = 0; j < ziegelbloecke.size(); j++) {
			for (int i = 0; i < anzgegner; i++) {

				if ((gegner[i].x >= ziegelbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= ziegelbloecke.get(j).getX() + 32)
						&& (gegner[i].y <= (ziegelbloecke.get(j).getY() + 32))
						&& (gegner[i].y >= (ziegelbloecke.get(j).getY() - 50) && (gegnerright[i]))) {

					gegner[i].x = gegner[i].x - ggspeed;
					hinrechts[i] = true;
					hinlinks[i] = false;
					hinunten[i] = false;
					hinoben[i] = false;
					ingameinitGegnerSchuss(i);

				} else if ((gegner[i].x <= ziegelbloecke.get(j).getX() + 32)
						&& (gegner[i].x >= ziegelbloecke.get(j).getX() - 50)
						&& (gegner[i].y <= ziegelbloecke.get(j).getY() + 32)
						&& (gegner[i].y >= (ziegelbloecke.get(j).getY() - 50) && (gegnerleft[i]))) {

					gegner[i].x = gegner[i].x + ggspeed;
					hinrechts[i] = false;
					hinlinks[i] = true;
					hinunten[i] = false;
					hinoben[i] = false;
					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y <= ziegelbloecke.get(j).getY() + 32)
						&& (gegner[i].x >= ziegelbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= ziegelbloecke.get(j).getX()+ 32)
						&& (gegner[i].y >= (ziegelbloecke.get(j).getY() - 50) && (gegnerup[i]))) {

					gegner[i].y = gegner[i].y + ggspeed;
					ingameinitGegnerSchuss(i);
					hinrechts[i] = false;
					hinlinks[i] = false;
					hinunten[i] = true;
					hinoben[i] = false;
				}

				else if ((gegner[i].y >= ziegelbloecke.get(j).getY() - 50)
						&& (gegner[i].x >= ziegelbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= ziegelbloecke.get(j).getX() + 32)
						&& (gegner[i].y <= (ziegelbloecke.get(j).getY() + 32))
						&& (gegnerdown[i])) {

					gegner[i].y = gegner[i].y - ggspeed;
					hinrechts[i] = false;
					hinlinks[i] = false;
					hinunten[i] = false;
					hinoben[i] = true;
					ingameinitGegnerSchuss(i);
				} else {

					hinrechts[i] = false;
					hinlinks[i] = false;
					hinunten[i] = false;
					hinoben[i] = false;
				}

			}
		}
	}

	public void contaktStuetzpunktgegner() {
		for (int j = 0; j < stuetzpunkte.size(); j++) {
			for (int i = 0; i < anzgegner; i++) {

				if ((gegner[i].x >= stuetzpunkte.get(j).getX() - 50)
						&& (gegner[i].x <= stuetzpunkte.get(j).getX() + 64)
						&& (gegner[i].y <= (stuetzpunkte.get(j).getY() + 64))
						&& (gegner[i].y >= (stuetzpunkte.get(j).getY() - 50) && (gegnerright[i]))) {
					gegner[i].x = gegner[i].x - ggspeed;
					ingameinitGegnerSchuss(i);

				} else if ((gegner[i].x <= stuetzpunkte.get(j).getX() + 64)
						&& (gegner[i].x >= stuetzpunkte.get(j).getX() - 50)
						&& (gegner[i].y <= stuetzpunkte.get(j).getY() + 64)
						&& (gegner[i].y >= (stuetzpunkte.get(j).getY() - 50) && (gegnerleft[i]))) {
					gegner[i].x = gegner[i].x + ggspeed;

					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y <= stuetzpunkte.get(j).getY() + 64)
						&& (gegner[i].x >= stuetzpunkte.get(j).getX() - 50)
						&& (gegner[i].x <= stuetzpunkte.get(j).getX() + 64)
						&& (gegner[i].y >= (stuetzpunkte.get(j).getY() - 50) && (gegnerright[i]))) {
					gegner[i].y = gegner[i].y + ggspeed;

					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y >= stuetzpunkte.get(j).getY() - 50)
						&& (gegner[i].x >= stuetzpunkte.get(j).getX() - 50)
						&& (gegner[i].x <= stuetzpunkte.get(j).getX() + 64)
						&& (gegner[i].y <= (stuetzpunkte.get(j).getY() + 64))
						&& (gegnerdown[i])) {
					gegner[i].y = gegner[i].y - ggspeed;

					ingameinitGegnerSchuss(i);

				}
			}
		}
	}

	public void contaktFlussgegner() { 
		for (int j = 0; j < flussbloecke.size(); j++) {
			for (int i = 0; i < anzgegner; i++) {

				if ((gegner[i].x >= flussbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= flussbloecke.get(j).getX() + 32)
						&& (gegner[i].y <= (flussbloecke.get(j).getY() + 32))
						&& (gegner[i].y >= (flussbloecke.get(j).getY() - 50) && (gegnerright[i]))) {

					gegner[i].x = gegner[i].x - ggspeed;

					ingameinitGegnerSchuss(i);

				} else if ((gegner[i].x <= flussbloecke.get(j).getX() + 32)
						&& (gegner[i].x >= flussbloecke.get(j).getX() - 50)
						&& (gegner[i].y <= flussbloecke.get(j).getY() + 32)
						&& (gegner[i].y >= (flussbloecke.get(j).getY() - 50) && (gegnerleft[i]))) {

					gegner[i].x = gegner[i].x + ggspeed;

					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y <= flussbloecke.get(j).getY() + 32)
						&& (gegner[i].x >= flussbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= flussbloecke.get(j).getX() + 32)
						&& (gegner[i].y >= (flussbloecke.get(j).getY() - 50) && (gegnerup[i]))) {

					gegner[i].y = gegner[i].y + ggspeed;

					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y >= flussbloecke.get(j).getY() - 50)
						&& (gegner[i].x >= flussbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= flussbloecke.get(j).getX() + 32)
						&& (gegner[i].y <= (flussbloecke.get(j).getY() + 32))
						&& (gegnerdown[i])) {

					gegner[i].y = gegner[i].y - ggspeed;

					ingameinitGegnerSchuss(i);

				}
			}
		}
	}

	public void contaktStahlwandgegner() { 
		for (int i = 0; i < anzgegner; i++) {

			for (int j = 0; j < stahlbloecke.size(); j++) {
				if ((gegner[i].x >= stahlbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= stahlbloecke.get(j).getX() + 32)
						&& (gegner[i].y <= (stahlbloecke.get(j).getY() + 32))
						&& (gegner[i].y >= (stahlbloecke.get(j).getY() - 50) && (gegnerright[i]))) {
					gegner[i].x = gegner[i].x - ggspeed;
					hinrechts[i] = true;
					hinlinks[i] = false;
					hinunten[i] = false;
					hinoben[i] = false;
					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].x <= stahlbloecke.get(j).getX() + 32)
						&& (gegner[i].x >= stahlbloecke.get(j).getX() - 50)
						&& (gegner[i].y <= stahlbloecke.get(j).getY() + 32)
						&& (gegner[i].y >= (stahlbloecke.get(j).getY() - 50) && (gegnerleft[i]))) {
					gegner[i].x = gegner[i].x + ggspeed;
					hinrechts[i] = false;
					hinlinks[i] = true;
					hinunten[i] = false;
					hinoben[i] = false;
					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y <= stahlbloecke.get(j).getY() + 32)
						&& (gegner[i].x >= stahlbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= stahlbloecke.get(j).getX() + 32)
						&& (gegner[i].y >= (stahlbloecke.get(j).getY() - 50) && (gegnerup[i]))) {
					gegner[i].y = gegner[i].y + ggspeed;
					hinrechts[i] = false;
					hinlinks[i] = false;
					hinunten[i] = true;
					hinoben[i] = false;
					ingameinitGegnerSchuss(i);

				}

				else if ((gegner[i].y >= stahlbloecke.get(j).getY() - 50)
						&& (gegner[i].x >= stahlbloecke.get(j).getX() - 50)
						&& (gegner[i].x <= stahlbloecke.get(j).getX() + 32)
						&& (gegner[i].y <= (stahlbloecke.get(j).getY() + 32))
						&& (gegnerdown[i])) {
					gegner[i].y = gegner[i].y - ggspeed;
					hinrechts[i] = false;
					hinlinks[i] = false;
					hinunten[i] = false;
					hinoben[i] = true;
					ingameinitGegnerSchuss(i);

				} else {
					hinrechts[i] = false;
					hinlinks[i] = false;
					hinunten[i] = false;
					hinoben[i] = false;

				}
			}
		}

	}

	public void contaktRandgegner() {
		// Rand
		;
		for (int i = 0; i < anzgegner; i++) {

			if ((gegner[i].x >= rand - 50) && (gegnerright[i])) {
				gegner[i].x = gegner[i].x - ggspeed;
				// Rechter Rand
				ingameinitGegnerSchuss(i);
				hinrechts[i] = true;
				hinlinks[i] = false;
				hinunten[i] = false;
				hinoben[i] = false;
			}

			else if ((gegner[i].x <= 0) && (gegnerleft[i])) {
				// Linker Rand
				gegner[i].x = gegner[i].x + ggspeed;
				hinrechts[i] = false;
				hinlinks[i] = true;
				hinunten[i] = false;
				hinoben[i] = false;
				ingameinitGegnerSchuss(i);

			} else if ((gegner[i].y >= statleiste + rand - 50)
					&& (gegnerdown[i])) {
				//
				gegner[i].y = gegner[i].y - ggspeed;
				hinrechts[i] = false;
				hinlinks[i] = false;
				hinunten[i] = true;
				hinoben[i] = false;
				ingameinitGegnerSchuss(i);

			} else if ((gegner[i].y <= statleiste) && (gegnerup[i])) {
				gegner[i].y = gegner[i].y + ggspeed;
				hinrechts[i] = false;
				hinlinks[i] = false;
				hinunten[i] = false;
				hinoben[i] = true;
				ingameinitGegnerSchuss(i);

			} else {
				hinrechts[i] = false;
				hinlinks[i] = false;
				hinunten[i] = false;
				hinoben[i] = false;
			}

		}

	}

	public void contaktGegnerHindernissschuss() {

		for (int j = 0; j < anzgegner; j++) {

			for (int i = 0; i < ziegelbloecke.size(); i++) {

				if ((gegnerschussy[j] <= ziegelbloecke.get(i).getY() + 32)
						&& (gegnerschussx[j] >= ziegelbloecke.get(i).getX() - 4)
						&& (gegnerschussx[j] <= ziegelbloecke.get(i).getX() + 32)
						&& (gegnerschussy[j] >= (ziegelbloecke.get(i).getY() - 4))) {

					collisionGegnerschuss(j);
					ingameinitGegnerSchuss(j);

					ziegelbloecke.remove(i);
				}
			}

			for (int i = 0; i < stahlbloecke.size(); i++) {

				if ((gegnerschussy[j] <= stahlbloecke.get(i).getY() + 32)
						&& (gegnerschussx[j] >= stahlbloecke.get(i).getX() - 4)
						&& (gegnerschussx[j] <= stahlbloecke.get(i).getX() + 32)
						&& (gegnerschussy[j] >= (stahlbloecke.get(i).getY() - 4))) {
					collisionGegnerschuss(j);
					ingameinitGegnerSchuss(j);
					// if (hartemun == true)
					//{
						stahlbloecke.remove(i);
					//}
					
					
					// stahlfeld[i] = 0;
					// }
				}
			}

			for (int i = 0; i < anzgegner; i++) {
				if ((gegnerschussx[i] >= rand - 4)) {
					ingameinitGegnerSchuss(i);
					collisionGegnerschuss(i);

				}

				else if ((gegnerschussx[i] <= 0)) {
					ingameinitGegnerSchuss(i);
					collisionGegnerschuss(i);
				} else if ((gegnerschussy[i] >= rand - 4)) {
					ingameinitGegnerSchuss(i);
					collisionGegnerschuss(i);

				} else if ((gegnerschussy[i] <= statleiste)) {
					ingameinitGegnerSchuss(i);
					collisionGegnerschuss(i);
				}

			}

		}
	}

	public void collisionschuss(int spieler) {

		players.get(spieler).helpifschussup = false;
		players.get(spieler).helpifschussdown= false;
		players.get(spieler).helpifschussleft= false;
		players.get(spieler).helpifschussright = false;

	}

	public void collisionGegnerschuss(int zgegner) {

		gghelpifschussup[zgegner] = false;
		gghelpifschussdown[zgegner] = false;
		gghelpifschussleft[zgegner] = false;
		gghelpifschussright[zgegner] = false;

	}

	public void schuss() {

		for (int i = 0; i < players.size(); i++) {

			if (players.get(i).helpifschussup == true) {

				players.get(i).schussy = players.get(i).schussy - schussspeed;

			}
			if (players.get(i).helpifschussdown == true) {

				players.get(i).schussy = players.get(i).schussy + schussspeed;;

			}
			if (players.get(i).helpifschussleft == true) {

				players.get(i).schussx = players.get(i).schussx - schussspeed;

			}
			if (players.get(i).helpifschussright == true) {

				players.get(i).schussx = players.get(i).schussx + schussspeed;
			}

		}

	}

	public void schussDesGegner() {

		for (int i = 0; i < anzgegner; i++) {

			if (gghelpifschussup[i] == true) {

				gegnerschussy[i] = gegnerschussy[i] - schussspeed;

			}
			if (gghelpifschussdown[i] == true) {

				gegnerschussy[i] = gegnerschussy[i] + schussspeed;

			}
			if (gghelpifschussleft[i] == true) {

				gegnerschussx[i] = gegnerschussx[i] - schussspeed;

			}
			if (gghelpifschussright[i] == true) {

				gegnerschussx[i] = gegnerschussx[i] + schussspeed;

			}
		}

	}

	public void obSchussDesGegner() {

		for (int i = 0; i < anzgegner; i++) {

			ggschusszahl[i] = 0;
			ggschusszahl[i] = (int) (Math.random() * 100);

			if (ggschusszahl[i] == 1) {

				if ((gghelpifschussup[i] == false)
						&& (gghelpifschussdown[i] == false)
						&& (gghelpifschussright[i] == false)
						&& (gghelpifschussleft[i] == false)) {

					ggifschuss[i] = true;

					if ((ggifschuss[i] == true) && ((gegnerup[i] == true))) {

						gghelpifschussup[i] = true;
						gghelpifschussdown[i] = false;
						gghelpifschussleft[i] = false;
						gghelpifschussright[i] = false;
					}
					if ((ggifschuss[i] == true) && ((gegnerdown[i] == true))) {

						gghelpifschussup[i] = false;
						gghelpifschussdown[i] = true;
						gghelpifschussleft[i] = false;
						gghelpifschussright[i] = false;
					}
					if ((ggifschuss[i] == true) && ((gegnerleft[i] == true))) {

						gghelpifschussup[i] = false;
						gghelpifschussdown[i] = false;
						gghelpifschussleft[i] = true;
						gghelpifschussright[i] = false;
					}
					if ((ggifschuss[i] == true) && ((gegnerright[i] == true))) {

						gghelpifschussup[i] = false;
						gghelpifschussdown[i] = false;
						gghelpifschussleft[i] = false;
						gghelpifschussright[i] = true;
						;
					}

					
					
				}

			}
		}

	}

	public void contaktSpielerSchussVonGegner() {
		// gegner trifft Spieler

		for (int i = 0; i < anzgegner; i++) {

			if ((gegnerschussy[i] <= players.get(0).getY() + 50)
					&& (gegnerschussx[i] >= players.get(0).getX() - 4)
					&& (gegnerschussx[i] <= players.get(0).getX() + 50)
					&& (gegnerschussy[i] >= (players.get(0).getY() - 4))) {

				collisionGegnerschuss(i);
				ingameinitGegnerSchuss(i);
				if (gameover == false) {
					players.get(0).leben--;
				}

				ingameinitSpieler(0);
				ingameinitSchuss(0);

			}

			if (players.size() == 2) {

				if ((gegnerschussy[i] <= players.get(1).getY() + 50)
						&& (gegnerschussx[i] >= players.get(1).getX() - 4)
						&& (gegnerschussx[i] <= players.get(1).getX() + 50)
						&& (gegnerschussy[i] >= (players.get(1).getY() - 4))) {

					collisionGegnerschuss(i);
					ingameinitGegnerSchuss(i);
					if (gameover == false) {
						players.get(1).leben--;;
					}

					ingameinitSpieler(1);
					ingameinitSchuss(1);
				}

			}

		}

	}

	public void contaktMitspielerschuss() {

		if (players.size() >= 2) {

			if ((players.get(0).schussy <=  players.get(1).getY()+ 50)
				&& (players.get(0).schussx >= players.get(1).getX() - 4)
				&& (players.get(0).schussx <= players.get(1).getX() + 50)
				&& (players.get(0).schussy >= (players.get(1).getY() - 4))) {
				
					collisionschuss(0);
					ingameinitSchuss(0);

				if(ingame){
					ingameinitSpieler(1);
					ingameinitSchuss(1);
					players.get(1).leben--;
					if (players.get(1).leben == 0) {
						players.get(0).anzahlwin++;
					}
				}
			
			} else if ((players.get(1).schussy <= players.get(0).getY()  + 50)
					&& (players.get(1).schussx >= players.get(0).getX() - 4)
					&& (players.get(1).schussx <= players.get(0).getX() + 50)
					&& (players.get(1).schussy >= (players.get(0).getY() - 4))) {

				collisionschuss(1);
				ingameinitSchuss(1);

				if(ingame){
					ingameinitSpieler(0);
					ingameinitSchuss(0);
					players.get(0).leben--;
					if (players.get(0).leben == 0) {
						players.get(1).anzahlwin++;
					}
				}
			}

		}

	}

	public void contaktGegnerschuss() {

		for (int i = 0; i < anzgegner; i++) {
			for (int j = 0; j < players.size(); j++) {

				if ((players.get(j).schussy <= gegner[i].y + 50)
						&& (players.get(j).schussx >= gegner[i].x - 4)
						&& (players.get(j).schussx <= gegner[i].x + 50)
						&& (players.get(j).schussy >= (gegner[i].y - 4))) {

					gegnerfeld[i] = 0;
					win++;
					players.get(j).kills++;
					collisionschuss(j);
					ingameinitSchuss(j);

				}

			}

		}

	}

	public void contaktStuetzpunktGegnerschuss() { // Gegner schießt auf
													// Stuetzpunkt --> Gameover
		for (int i = 0; i < stuetzpunkte.size(); i++) {

			for (int j = 0; j < anzgegner; j++) {

				if ((gegnerschussy[j] <= stuetzpunkte.get(i).getY() + 64)
						&& (gegnerschussx[j] >= stuetzpunkte.get(i).getX() - 4)
						&& (gegnerschussx[j] <= stuetzpunkte.get(i).getX() + 64)
						&& (gegnerschussy[j] >= (stuetzpunkte.get(i).getY() - 4))) {
					gameover = true;
					ingameinitGegnerSchuss(j);
					// Thread.sleep(500);

					// timer.stop();

				}
			}
		}
	}

	public void contaktStuetzpunktschuss() { // Spieler schießt auf Stuetzpunkt
												// --> Gameover
		// boolean hilf = true;

			for (int j = 0; j < players.size(); j++) {
				if(players.size() >= 1){
					if ((players.get(j).schussy <= stuetzpunkte.get(0).getY() + 64)
							&& (players.get(j).schussx >= stuetzpunkte.get(0).getX() - 4)
							&& (players.get(j).schussx <= stuetzpunkte.get(0).getX() + 64)
							&& (players.get(j).schussy >= (stuetzpunkte.get(0).getY() - 4))) {
						gameover = true;
						collisionschuss(j);
						ingameinitSchuss(j);

						// if (hilf == true){
						if (ingame) {

							if (j == 0) {
								p2win = true;
								players.get(1).anzahlwin++;
							}
							if (j == 1) {
								p2win = true;
								players.get(1).anzahlwin++;
							}
						}
						ingame = false;
						// }
						// hilf = false;

						// timer.stop();
					}	
				}
				
				if(players.size() >= 2){
					if ((players.get(j).schussy <= stuetzpunkte.get(1).getY() + 64)
							&& (players.get(j).schussx >= stuetzpunkte.get(1).getX() - 4)
							&& (players.get(j).schussx <= stuetzpunkte.get(1).getX() + 64)
							&& (players.get(j).schussy >= (stuetzpunkte.get(1).getY() - 4))) 
						{
								gameover = true;
								collisionschuss(j);
								ingameinitSchuss(j);

								// if (hilf == true){
								if (ingame) {

									if (j == 0) {
										p1win = true;
										players.get(0).anzahlwin++;
									}
									if (j == 1) {
										p1win = true;
										players.get(0).anzahlwin++;
									}
								}
								ingame = false;
								// }
								// hilf = false;

								// timer.stop();
						}
				}
				
			}		
			
	}

	public void contaktGegner() {

		for (int j = 0; j < anzgegner; j++) {

			for (int i = 0; i < players.size(); i++) {

				if ((players.get(i).getX() >= gegner[j].x - 50)
						&& (players.get(i).getX() <= gegner[j].x + 50)
						&& (players.get(i).getY() <= (gegner[j].y + 50))
						&& (players.get(i).getY() >= (gegner[j].y - 50) && (players.get(i).right))) {
					players.get(i).setX(players.get(i).getX() - players.get(i).speed);
					ingameinitSchuss(i);

					collisionschuss(i);

				} else if ((players.get(i).getX() <= gegner[j].x + 50)
						&& (players.get(i).getX() >= gegner[j].x - 50)
						&& (players.get(i).getY() <= gegner[j].y + 50)
						&& (players.get(i).getY() >= (gegner[j].y - 50) && (players.get(i).left))) {
					players.get(i).setX(players.get(i).getX() + players.get(i).speed);

					ingameinitSchuss(i);

					collisionschuss(i);
				}

				else if ((players.get(i).getY() <= gegner[j].y + 50)
						&& (players.get(i).getX() >= gegner[j].x - 50)
						&& (players.get(i).getX() <= gegner[j].x + 50)
						&& (players.get(i).getY() >= (gegner[j].y - 50) && (players.get(i).up))) {
					players.get(i).setY(players.get(i).getY() + players.get(i).speed);
					ingameinitSchuss(i);

					collisionschuss(i);
				}

				else if ((players.get(i).getY() >= gegner[j].y - 50)
						&& (players.get(i).getX() >= gegner[j].x - 50)
						&& (players.get(i).getX() <= gegner[j].x + 50)
						&& (players.get(i).getY() <= (gegner[j].y + 50)) && (players.get(i).down)) {
					players.get(i).setY(players.get(i).getY() - players.get(i).speed);

					ingameinitSchuss(i);

					collisionschuss(i);
				}

			}

		}

	}

	public void contaktStuetzpunkt() {

		for (int j = 0; j < stuetzpunkte.size(); j++) {
			for (int i = 0; i < players.size(); i++) {

				if ((players.get(i).getX() >= stuetzpunkte.get(j).getX() - 50)
						&& (players.get(i).getX() <= stuetzpunkte.get(j).getX() + 64)
						&& (players.get(i).getY() <= (stuetzpunkte.get(j).getY() + 64))
						&& (players.get(i).getY() >= (stuetzpunkte.get(j).getY() - 50) && (players.get(i).right))) {
					players.get(i).setX(players.get(i).getX() - players.get(i).speed);
					ingameinitSchuss(i);

					collisionschuss(i);

				} else if ((players.get(i).getX() <= stuetzpunkte.get(j).getX() + 64)
						&& (players.get(i).getX() >= stuetzpunkte.get(j).getX() - 50)
						&& (players.get(i).getY() <= stuetzpunkte.get(j).getY() + 64)
						&& (players.get(i).getY() >= (stuetzpunkte.get(j).getY() - 50) && (players.get(i).left))) {
					players.get(i).setX(players.get(i).getX() + players.get(i).speed);
					ingameinitSchuss(i);

					collisionschuss(i);
				}

				else if ((players.get(i).getY() <= stuetzpunkte.get(j).getY() + 64)
						&& (players.get(i).getX() >= stuetzpunkte.get(j).getX() - 50)
						&& (players.get(i).getX() <= stuetzpunkte.get(j).getX() + 64)
						&& (players.get(i).getY() >= (stuetzpunkte.get(j).getY() - 50) && (players.get(i).up))) {
					players.get(i).setY(players.get(i).getY() + players.get(i).speed);
					ingameinitSchuss(i);

					collisionschuss(i);
				}

				else if ((players.get(i).getY() >= stuetzpunkte.get(j).getY() - 50)
						&& (players.get(i).getX() >= stuetzpunkte.get(j).getX() - 50)
						&& (players.get(i).getX() <= stuetzpunkte.get(j).getX() + 64)
						&& (players.get(i).getY() <= (stuetzpunkte.get(j).getY() + 64)) && (players.get(i).down)) {
					players.get(i).setY(players.get(i).getY() - players.get(i).speed);

					ingameinitSchuss(i);

					collisionschuss(i);
				}

			}

		}

	}

	public void contaktHindernissschuss(int spieler) {

		for (int i = 0; i < ziegelbloecke.size(); i++) {

			if ((players.get(spieler).schussy <= ziegelbloecke.get(i).getY() + 32)
					&& (players.get(spieler).schussx >= ziegelbloecke.get(i).getX() - 4)
					&& (players.get(spieler).schussx <= ziegelbloecke.get(i).getX() + 32)
					&& (players.get(spieler).schussy >= (ziegelbloecke.get(i).getY() - 4))) {

				collisionschuss(spieler);
				ingameinitSchuss(spieler);
				ziegelbloecke.remove(i);
			}
		}

		for (int i = 0; i < stahlbloecke.size(); i++) {

			if ((players.get(spieler).schussy <= stahlbloecke.get(i).getY() + 32)
					&& (players.get(spieler).schussx >= stahlbloecke.get(i).getX() - 4)
					&& (players.get(spieler).schussx <= stahlbloecke.get(i).getX() + 32)
					&& (players.get(spieler).schussy >= (stahlbloecke.get(i).getY() - 4))) {
				collisionschuss(spieler);
				ingameinitSchuss(spieler);
				if (players.get(spieler).hartemun == true) {
					stahlbloecke.remove(i);
				}

			}

		}

		if ((players.get(spieler).schussx >= rand - 4)) {

			ingameinitSchuss(spieler);
			collisionschuss(spieler);

		}

		if ((players.get(spieler).schussx <= 0)) {

			ingameinitSchuss(spieler);
			collisionschuss(spieler);
		}
		if ((players.get(spieler).schussy >= rand - 4)) {

			ingameinitSchuss(spieler);
			collisionschuss(spieler);

		}
		if ((players.get(spieler).schussy <= statleiste)) {

			ingameinitSchuss(spieler);
			collisionschuss(spieler);
		}

	}

	public void contakteis() { 
	
		for (int j = 0; j < eisbloecke.size(); j++) {
			for (int i = 0; i < players.size(); i++) {
				if ((players.get(i).getX() >= eisbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= eisbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (eisbloecke.get(j).getY() + 32))
						&& (players.get(i).getY() >= (eisbloecke.get(j).getY() - 50) && (players.get(i).right))) {
					players.get(i).speed = 2;
					aufeis = true;

				}

				else if ((players.get(i).getX() <= eisbloecke.get(j).getX() + 32)
						&& (players.get(i).getX() >= eisbloecke.get(j).getX() - 50)
						&& (players.get(i).getY() <= eisbloecke.get(j).getY() + 32)
						&& (players.get(i).getY() >= (eisbloecke.get(j).getY() - 50) && (players.get(i).left))) {
					players.get(i).speed = 2;
					aufeis = true;
				}

				else if ((players.get(i).getY() <= eisbloecke.get(j).getY() + 32)
						&& (players.get(i).getX() >= eisbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= eisbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() >= (eisbloecke.get(j).getY() - 50) && (players.get(i).up))) {
					players.get(i).speed = 2;
					aufeis = true;
				}

				else if ((players.get(i).getY() >= eisbloecke.get(j).getY() - 50)
						&& (players.get(i).getX() >= eisbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= eisbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (eisbloecke.get(j).getY() + 32)) && (players.get(i).down)) {
					players.get(i).speed = 2;
					
				} 
				else{
					aufeis = false;
					
				}
			}

		}

	}

	public void contaktstahlwand() { 

		for (int j = 0; j < stahlbloecke.size(); j++) {
			for (int i = 0; i < players.size(); i++) {

				if ((players.get(i).getX() >= stahlbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= stahlbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (stahlbloecke.get(j).getY() + 32))
						&& (players.get(i).getY() >= (stahlbloecke.get(j).getY() - 50) && (players.get(i).right))) {
					players.get(i).setX(players.get(i).getX() - players.get(i).speed );

					ingameinitSchuss(i);

				}

				else if ((players.get(i).getX() <= stahlbloecke.get(j).getX() + 32)
						&& (players.get(i).getX() >= stahlbloecke.get(j).getX() - 50)
						&& (players.get(i).getY() <= stahlbloecke.get(j).getY() + 32)
						&& (players.get(i).getY() >= (stahlbloecke.get(j).getY() - 50) && (players.get(i).left))) {
					players.get(i).setX(players.get(i).getX() + players.get(i).speed );

					ingameinitSchuss(i);

				}

				else if ((players.get(i).getY() <= stahlbloecke.get(j).getY() + 32)
						&& (players.get(i).getX() >= stahlbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= stahlbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() >= (stahlbloecke.get(j).getY() - 50) && (players.get(i).up))) {
					players.get(i).setY(players.get(i).getY() + players.get(i).speed );

					ingameinitSchuss(i);

				}

				else if ((players.get(i).getY() >= stahlbloecke.get(j).getY() - 50)
						&& (players.get(i).getX() >= stahlbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= stahlbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (stahlbloecke.get(j).getY() + 32)) && (players.get(i).down)) {
					players.get(i).setY(players.get(i).getY() - players.get(i).speed );

					ingameinitSchuss(i);

				} 
			}

		}

	}

	public void contaktfluss(int spieler) {

		for (int j = 0; j < flussbloecke.size(); j++) {
			// Bleibt hängen, liegt an der Varialenübergabe

			if ((players.get(spieler).getX() >= flussbloecke.get(j).getX() - 50)
					&& (players.get(spieler).getX() <= flussbloecke.get(j).getX() + 32)
					&& (players.get(spieler).getY() <= (flussbloecke.get(j).getY() + 32))
					&& (players.get(spieler).getY() >= (flussbloecke.get(j).getY() - 50) && (players.get(spieler).right))) {

				if (players.get(spieler).schiff == false) {

					players.get(spieler).setX( players.get(spieler).getX() - players.get(spieler).speed);

					ingameinitSchuss(spieler);

				}

			} else if ((players.get(spieler).getX() <= flussbloecke.get(j).getX() + 32)
					&& (players.get(spieler).getX() >= flussbloecke.get(j).getX() - 50)
					&& (players.get(spieler).getY() <= flussbloecke.get(j).getY() + 32)
					&& (players.get(spieler).getY() >= (flussbloecke.get(j).getY() - 50) && (players.get(spieler).left))) {

				if (players.get(spieler).schiff == false) {
					players.get(spieler).setX( players.get(spieler).getX() + players.get(spieler).speed);

					ingameinitSchuss(spieler);

				}

			}

			else if ((players.get(spieler).getY() <= flussbloecke.get(j).getY() + 32)
					&& (players.get(spieler).getX() >= flussbloecke.get(j).getX() - 50)
					&& (players.get(spieler).getX() <= flussbloecke.get(j).getX() + 32)
					&& (players.get(spieler).getY() >= (flussbloecke.get(j).getY() - 50) && (players.get(spieler).up))) {

				if (players.get(spieler).schiff == false) {

					players.get(spieler).setY( players.get(spieler).getY() + players.get(spieler).speed);

					ingameinitSchuss(spieler);

				}

			}

			else if ((players.get(spieler).getY() >= flussbloecke.get(j).getY() - 50)
					&& (players.get(spieler).getX() >= flussbloecke.get(j).getX() - 50)
					&& (players.get(spieler).getX() <= flussbloecke.get(j).getX() + 32)
					&& (players.get(spieler).getY() <= (flussbloecke.get(j).getY() + 32)) && (players.get(spieler).down)) {
				if (players.get(spieler).schiff == false) {

					players.get(spieler).setY( players.get(spieler).getY() - players.get(spieler).speed);

					ingameinitSchuss(spieler);

				}

			} 

		}

	}
	
	public void contaktbusch() {

		
		for (int j = 0; j < buschbloecke.size(); j++) {
			// Bleibt hängen, liegt an der Varialenübergabe
			for (int i = 0; i < players.size(); i++) {
				if ((players.get(i).getX() >= buschbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= buschbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (buschbloecke.get(j).getY() + 32))
						&& (players.get(i).getY() >= (buschbloecke.get(j).getY() - 50) && (players.get(i).right))) {
						players.get(i).imbusch = true;
						

				}
				
				
				if ((players.get(i).getX() <= buschbloecke.get(j).getX() + 32)
						&& (players.get(i).getX() >= buschbloecke.get(j).getX() - 50)
						&& (players.get(i).getY() <= buschbloecke.get(j).getY() + 32)
						&& (players.get(i).getY() >= (buschbloecke.get(j).getY() - 50) && (players.get(i).left))) {
						players.get(i).imbusch = true;
	
				}
			
	
				if ((players.get(i).getY() <= buschbloecke.get(j).getY() + 32)
						&& (players.get(i).getX() >= buschbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= buschbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() >= (buschbloecke.get(j).getY() - 50) && (players.get(i).up))) {
						players.get(i).imbusch = true;
				}
				if ((players.get(i).getY() >= buschbloecke.get(j).getY() - 50)
						&& (players.get(i).getX() >= buschbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= buschbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (buschbloecke.get(j).getY() + 32)) && (players.get(i).down)) {
						players.get(i).imbusch = true;
				} 

				
			}
		}

	}

	public void contaktMitspieler() {

		if (players.size() >= 2) {

			if ((players.get(0).getX() >= players.get(1).getX() - 50) && (players.get(0).getX() <= players.get(1).getX() + 50)
					&& (players.get(0).getY() <= players.get(1).getY() + 50)
					&& (players.get(0).getY() >= (players.get(1).getY() - 50) && (players.get(0).right))) {
				players.get(0).setX(players.get(0).getX() - players.get(0).speed);
				ingameinitSchuss(0);
				collisionschuss(0);

			} else if ((players.get(0).getX() <= players.get(1).getX() + 50)
					&& (players.get(0).getX() >= players.get(1).getX() - 50)
					&& (players.get(0).getY() <= players.get(1).getY() + 50)
					&& (players.get(0).getY() >= (players.get(1).getY() - 50) && (players.get(0).left))) {
				players.get(0).setX(players.get(0).getX() + players.get(0).speed);

				ingameinitSchuss(0);
				collisionschuss(0);
			}

			else if ((players.get(0).getY() <= players.get(1).getY() + 50) && (players.get(0).getX() >= players.get(1).getX() - 50)
					&& (players.get(0).getX() <= players.get(1).getX() + 50)
					&& (players.get(0).getY() >= (players.get(1).getY() - 50) && (players.get(0).up))) {
				players.get(0).setY(players.get(0).getY() + players.get(0).speed);

				ingameinitSchuss(0);
				collisionschuss(0);
			}

			else if ((players.get(0).getY() >= players.get(1).getY() - 50) && (players.get(0).getX() >= players.get(1).getX() - 50)
					&& (players.get(0).getX() <= players.get(1).getX() + 50)
					&& (players.get(0).getY() <= (players.get(1).getY() + 50)) && (players.get(0).down)) {
				players.get(0).setY(players.get(0).getY() - players.get(0).speed);

				ingameinitSchuss(0);
				collisionschuss(0);
			} 
			
			if ((players.get(1).getX() >= players.get(0).getX() - 50) && (players.get(1).getX() <= players.get(0).getX() + 50)
					&& (players.get(1).getY() <= players.get(0).getY() + 50)
					&& (players.get(1).getY() >= (players.get(0).getY() - 50) && (players.get(1).right))) {
				players.get(1).setX(players.get(1).getX() - players.get(1).speed);
				ingameinitSchuss(1);
				collisionschuss(1);

			} else if ((players.get(1).getX() <= players.get(0).getX() + 50)
					&& (players.get(1).getX() >= players.get(0).getX() - 50)
					&& (players.get(1).getY() <= players.get(0).getY() + 50)
					&& (players.get(1).getY() >= (players.get(0).getY() - 50) && (players.get(1).left))) {
				players.get(1).setX(players.get(1).getX() + players.get(1).speed);

				ingameinitSchuss(1);
				collisionschuss(1);
			}

			else if ((players.get(1).getY() <= players.get(0).getY() + 50) && (players.get(1).getX() >= players.get(0).getX() - 50)
					&& (players.get(1).getX() <= players.get(0).getX() + 50)
					&& (players.get(1).getY() >= (players.get(0).getY() - 50) && (players.get(1).up))) {
				players.get(1).setY(players.get(1).getY() + players.get(1).speed);

				ingameinitSchuss(1);
				collisionschuss(1);
			}

			else if ((players.get(1).getY() >= players.get(0).getY() - 50) && (players.get(1).getX() >= players.get(0).getX() - 50)
					&& (players.get(1).getX() <= players.get(0).getX() + 50)
					&& (players.get(1).getY() <= (players.get(0).getY() + 50)) && (players.get(1).down)) {
				players.get(1).setY(players.get(1).getY() - players.get(1).speed);
				ingameinitSchuss(1);
				collisionschuss(1);
			} 

		}

	}

	public void contaktziegel() {

		for (int j = 0; j < ziegelbloecke.size(); j++) {
			for (int i = 0; i < players.size(); i++) {

				if ((players.get(i).getX() >= ziegelbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= ziegelbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (ziegelbloecke.get(j).getY() + 32))
						&& (players.get(i).getY() >= (ziegelbloecke.get(j).getY() - 50) && (players.get(i).right))) {
					players.get(i).setX(players.get(i).getX() - players.get(i).speed); 

					ingameinitSchuss(i);

				} else if ((players.get(i).getX() <= ziegelbloecke.get(j).getX() + 32)
						&& (players.get(i).getX() >= ziegelbloecke.get(j).getX() - 50)
						&& (players.get(i).getY() <= ziegelbloecke.get(j).getY() + 32)
						&& (players.get(i).getY() >= (ziegelbloecke.get(j).getY() - 50) && (players.get(i).left))) {
					players.get(i).setX(players.get(i).getX() + players.get(i).speed); 


					ingameinitSchuss(i);

				}

				else if ((players.get(i).getY() <= ziegelbloecke.get(j).getY() + 32)
						&& (players.get(i).getX() >= ziegelbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= ziegelbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() >= (ziegelbloecke.get(j).getY() - 50) && (players.get(i).up))) {
					players.get(i).setY(players.get(i).getY() + players.get(i).speed); 


					ingameinitSchuss(i);

				}

				else if ((players.get(i).getY() >= ziegelbloecke.get(j).getY() - 50)
						&& (players.get(i).getX() >= ziegelbloecke.get(j).getX() - 50)
						&& (players.get(i).getX() <= ziegelbloecke.get(j).getX() + 32)
						&& (players.get(i).getY() <= (ziegelbloecke.get(j).getY() + 32)) && (players.get(i).down)) {
					players.get(i).setY(players.get(i).getY() - players.get(i).speed); 

					ingameinitSchuss(i);

				} else {


				}

			}

		}

	}

	public void spawnGegner() {

		for (int i = 0; i < anzgegner; i++) {

			if ((gegnerfeld[i] == 2)) {
				gegner[i].x = nirvana;
				gegner[i].y = nirvana;

			}

			hilfproofnirvana[i] = (int) (Math.random() * 500);

			if ((hilfproofnirvana[i] == 1) && (gegnerfeld[i] == 2)) {

				gegnerfeld[i] = 1;

				gegner[i].x = 10 + ggspawnhilf * 382;
				gegner[i].y = 25;

				ggspawnhilf = ggspawnhilf + 1;

				if (ggspawnhilf == 3) {

					ggspawnhilf = 0;
				}
			}
		}

	}

	public void proofwin() {

		if ((win == anzgegner) && (onevsone == false)) {

			level++;
			timer.stop();

			//init();

		}
	}

	public void proofgameover() {

		if (players.size() >= 1) {
			if ((players.get(0).leben == 0)) {
				p2win = true;
				gameover = true;
				// timer.stop();
				ingame = false;
				for (int i = 0; i < players.size(); i++) {
					players.get(i).schnell = false;
					players.get(i).schiff = false;
					players.get(i).hartemun = false;
				}
	
			}
		}
		if (players.size() >= 2) {
			if (players.get(1).leben == 0) {
				p1win = true;
				gameover = true;
				// timer.stop();
				ingame = false;
				for (int i = 0; i < players.size(); i++) {
					players.get(i).schnell = false;
					players.get(i).schiff = false;
					players.get(i).hartemun = false;
				}
			}
		}

	}

	public void proofgegner() {

		for (int i = 0; i < anzgegner; i++) {
			if (gegnerfeld[i] == 0) {

				gegner[i].x = nirvana;
				gegner[i].y = nirvana;

				symbolgegnerx[i] = nirvana;
				symbolgegnery[i] = nirvana;

			}

		}

	}



	public void prooflevel() {

		if (win == anzgegner) {

			level++;
			win = 0;

			// init(); //initialisiert fatale Folgen
		}
	}


	public void move(int spieler) {


		
		

			if (players.get(spieler).up) {

				players.get(spieler).setY(players.get(spieler).getY() - players.get(spieler).speed);
			}
			if (players.get(spieler).left) {
				players.get(spieler).setX(players.get(spieler).getX() - players.get(spieler).speed);

			}

			if (players.get(spieler).right) {
				players.get(spieler).setX(players.get(spieler).getX() + players.get(spieler).speed);

			}

			if (players.get(spieler).down) {
				players.get(spieler).setY(players.get(spieler).getY() + players.get(spieler).speed);

			}

		

		if (players.get(spieler).schnell == true) {
			players.get(spieler).speed = 12;
		}else if(players.get(spieler).schnell != true && !aufeis){
			players.get(spieler).speed = 7;
		}

	}

	public void moveschuss() {

		for (int i = 0; i < players.size(); i++) {

			if ((players.get(i).helpifschussup == false) && (players.get(i).helpifschussdown == false)
					&& (players.get(i).helpifschussright == false)
					&& (players.get(i).helpifschussleft == false)) {

				if (players.get(i).up) {

					players.get(i).schussy = players.get(i).schussy - players.get(i).speed;

				}
				if (players.get(i).left) {
					players.get(i).schussx = players.get(i).schussx - players.get(i).speed;

				}

				if (players.get(i).right) {
					players.get(i).schussx = players.get(i).schussx + players.get(i).speed;

				}

				if (players.get(i).down) {
					players.get(i).schussy = players.get(i).schussy + players.get(i).speed;

				}

			}

		}
	}

	public void moveGegnerschuss() {

		for (int i = 0; i < anzgegner; i++) {

			if ((gghelpifschussup[i] == false)
					&& (gghelpifschussdown[i] == false)
					&& (gghelpifschussright[i] == false)
					&& (gghelpifschussleft[i] == false)) {

				if (gegnerup[i]) {

					gegnerschussy[i] = gegnerschussy[i] - ggspeed;

				}
				if (gegnerleft[i]) {
					gegnerschussx[i] = gegnerschussx[i] - ggspeed;

				}

				if (gegnerright[i]) {
					gegnerschussx[i] = gegnerschussx[i] + ggspeed;

				}

				if (gegnerdown[i]) {
					gegnerschussy[i] = gegnerschussy[i] + ggspeed;

				}
			}

		}

	}

	public void moveGegner() {

		for (int i = 0; i < anzgegner; i++) {

			zufallrichtug[i] = (int) (Math.random() * 400);

			if (zufallrichtug[i] == 1) {
				hilfzufallrichtug[i] = 1; // rechts

			}
			if (zufallrichtug[i] == 2) {
				hilfzufallrichtug[i] = 2; // unten
			}
			if (zufallrichtug[i] == 3) {
				hilfzufallrichtug[i] = 3; // links
			}
			if (zufallrichtug[i] == 4) {
				hilfzufallrichtug[i] = 4; // oben
			}

			if (hinlinks[i] == true) {
				int hilf = 0;

				do {
					hilf = (int) (Math.random() * 400);
				} while ((hilf != 2) && (hilf != 1) && (hilf != 4));
				hilfzufallrichtug[i] = hilf;

			}
			if (hinrechts[i] == true) {
				int hilf = 0;
				do {
					hilf = (int) (Math.random() * 400);
				} while ((hilf != 2) && (hilf != 3) && (hilf != 4));
				hilfzufallrichtug[i] = hilf;

			}
			if (hinunten[i] == true) {
				int hilf = 0;
				do {
					hilf = (int) (Math.random() * 400);
				} while ((hilf != 1) && (hilf != 3) && (hilf != 4));
				hilfzufallrichtug[i] = hilf;
			}
			if (hinoben[i] == true) {

				int hilf = 0;
				do {
					hilf = (int) (Math.random() * 400);
				} while ((hilf != 1) && (hilf != 3) && (hilf != 2));
				hilfzufallrichtug[i] = hilf;

			}

			// for (int j = 0;j < players.size();j++){
			// if ((tankx[j] == gegner[i].x)&&(tanky[j] < gegner[i].y)){
			//
			// hilfzufallrichtug[i] = 4; //oben
			//
			// }
			//
			// }
			// for (int j = 0;j < players.size();j++){
			// if ((tankx[j] == gegner[i].x)&&(tanky[j] > gegner[i].y)){
			//
			// hilfzufallrichtug[i] = 2;
			//
			// }
			//
			// }
			// for (int j = 0;j < players.size();j++){
			// if ((tanky[j] == gegner[i].y)&&(tankx[j] > gegner[i].x)){
			//
			// hilfzufallrichtug[i] = 1;
			//
			// }
			//
			// }
			// for (int j = 0;j < players.size();j++){
			// if ((tanky[j] == gegner[i].y)&&(tankx[j] < gegner[i].x)){
			//
			// hilfzufallrichtug[i] = 3;
			//
			// }
			//
			// }

			if (hilfzufallrichtug[i] == 1) {
				gegner[i].x = gegner[i].x + ggspeed;

				gegnerup[i] = false;
				gegnerdown[i] = false;
				gegnerleft[i] = false;
				gegnerright[i] = true;

			} else if (hilfzufallrichtug[i] == 2) {
				gegner[i].y = gegner[i].y + ggspeed;

				gegnerup[i] = false;
				gegnerdown[i] = true;
				gegnerleft[i] = false;
				gegnerright[i] = false;

			} else if (hilfzufallrichtug[i] == 3) {
				gegner[i].x = gegner[i].x - ggspeed;

				gegnerup[i] = false;
				gegnerdown[i] = false;
				gegnerleft[i] = true;
				gegnerright[i] = false;

			} else if (hilfzufallrichtug[i] == 4) {
				gegner[i].y = gegner[i].y - ggspeed;

				gegnerup[i] = true;
				gegnerdown[i] = false;
				gegnerleft[i] = false;
				gegnerright[i] = false;

			}

			if (gegnerup[i]) {
				gegner[i].setzeString("GegnerTankup0.png");

			} else if (gegnerdown[i]) {
				gegner[i].setzeString("GegnerTankdown0.png");

			} else if (gegnerleft[i]) {
				gegner[i].setzeString("GegnerTankleft0.png");

			} else if (gegnerright[i]) {
				gegner[i].setzeString("GegnerTankright0.png");

			}

		}
	}

	// Einlesen über die Pfeiltasten etc.
	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			// try {
			// soundFahr();
			// } catch (UnsupportedAudioFileException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (LineUnavailableException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			if ((key == KeyEvent.VK_RIGHT)) {
				players.get(0).right = true;
				players.get(0).up = false;
				players.get(0).down = false;
				if ((key == KeyEvent.VK_RIGHT && (players.get(0).left))) {
					players.get(0).right = true;
					players.get(0).up = false;
					players.get(0).down = false;
					players.get(0).left = false;

				}

			}

			if ((key == KeyEvent.VK_UP)) {

				players.get(0).up = true;
				players.get(0).right = false;
				players.get(0).left = false;
				if ((key == KeyEvent.VK_UP) && (players.get(0).down)) {
					players.get(0).up = true;
					players.get(0).right = false;
					players.get(0).left = false;
					players.get(0).down = false;

				}

			}

			if ((key == KeyEvent.VK_DOWN)) {

				players.get(0).down = true;
				players.get(0).right = false;
				players.get(0).left = false;
				if ((key == KeyEvent.VK_DOWN) && (players.get(0).up)) {
					players.get(0).down = true;
					players.get(0).right = false;
					players.get(0).left = false;
					players.get(0).up = false;

				}

			}
			if ((key == KeyEvent.VK_LEFT)) {
				players.get(0).left = true;
				players.get(0).up = false;
				players.get(0).down = false;
				if ((key == KeyEvent.VK_LEFT) && (players.get(0).right)) {
					players.get(0).left = true;
					players.get(0).up = false;
					players.get(0).down = false;
					players.get(0).right = false;
				}

			}

			if ((key == KeyEvent.VK_NUMPAD0)) {
				if ((players.get(0).helpifschussup == false)
						&& (players.get(0).helpifschussdown == false)
						&& (players.get(0).helpifschussright == false)
						&& (players.get(0).helpifschussleft == false)) {

					players.get(0).ifschuss = true;

//					try {
//						soundSchuss();
//					} catch (UnsupportedAudioFileException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (LineUnavailableException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}

					if ((players.get(0).ifschuss == true)
							&& ((players.get(0).up == true) || (players.get(0).uphilf == true))) {

						players.get(0).helpifschussup = true;
						players.get(0).helpifschussdown = false;
						players.get(0).helpifschussleft = false;
						players.get(0).helpifschussright = false;
					}
					if ((players.get(0).ifschuss == true)
							&& ((players.get(0).down == true) || (players.get(0).downhilf == true))) {

						players.get(0).helpifschussup = false;
						players.get(0).helpifschussdown = true;
						players.get(0).helpifschussleft = false;
						players.get(0).helpifschussright = false;
					}
					if ((players.get(0).ifschuss == true)
							&& ((players.get(0).left == true) || (players.get(0).lefthilf == true))) {

						players.get(0).helpifschussup = false;
						players.get(0).helpifschussdown = false;
						players.get(0).helpifschussleft = true;
						players.get(0).helpifschussright = false;
					}
					if ((players.get(0).ifschuss == true)
							&& ((players.get(0).right == true) || (players.get(0).righthilf == true))) {

						players.get(0).helpifschussup = false;
						players.get(0).helpifschussdown = false;
						players.get(0).helpifschussleft = false;
						players.get(0).helpifschussright = true;
						;
					}
				}
			}
			// //////////////////// Spieler 2

			if ((key == KeyEvent.VK_D)) {
				players.get(1).right = true;
				players.get(1).up = false;
				players.get(1).down = false;
				if ((key == KeyEvent.VK_D && (players.get(1).left))) {
					players.get(1).right = true;
					players.get(1).up = false;
					players.get(1).down = false;
					players.get(1).left = false;
				}
			}

			if ((key == KeyEvent.VK_W)) {

				players.get(1).up = true;
				players.get(1).right = false;
				players.get(1).left = false;
				if ((key == KeyEvent.VK_W) && (players.get(1).down)) {
					players.get(1).up = true;
					players.get(1).right = false;
					players.get(1).left = false;
					players.get(1).down = false;
				}
			}

			if ((key == KeyEvent.VK_S)) {

				players.get(1).down = true;
				players.get(1).right = false;
				players.get(1).left = false;
				if ((key == KeyEvent.VK_S) && (players.get(1).up)) {
					players.get(1).down = true;
					players.get(1).right = false;
					players.get(1).left = false;
					players.get(1).up = false;

				}
			}
			if ((key == KeyEvent.VK_A)) {
				players.get(1).left = true;
				players.get(1).up = false;
				players.get(1).down = false;
				if ((key == KeyEvent.VK_A) && (players.get(1).right)) {
					players.get(1).left = true;
					players.get(1).up = false;
					players.get(1).down = false;
					players.get(1).right = false;
				}
			}
			// ////// Sonst Tasten
			if ((key == KeyEvent.VK_R)) {
				gameover = false;
				replay = true;
				try {
					replay();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}

			if (key == KeyEvent.VK_P) {
				if (timer.isRunning()) {
					pause = true;
					timer.stop();

				} else {

					pause = false;

					timer.start();

				}
			}

			if ((key == KeyEvent.VK_CONTROL)) {
				if ((players.get(1).helpifschussup == false)
						&& (players.get(1).helpifschussdown == false)
						&& (players.get(1).helpifschussright == false)
						&& (players.get(1).helpifschussleft == false)) {

					players.get(1).ifschuss = true;

//					try {
//						soundSchuss();
//					} catch (UnsupportedAudioFileException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (LineUnavailableException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}

					if ((players.get(1).ifschuss == true)
							&& ((players.get(1).up == true) || (players.get(1).uphilf == true))) {

						players.get(1).helpifschussup = true;
						players.get(1).helpifschussdown = false;
						players.get(1).helpifschussleft = false;
						players.get(1).helpifschussright = false;
					}
					if ((players.get(1).ifschuss == true)
							&& ((players.get(1).down == true) || (players.get(1).downhilf == true))) {

						players.get(1).helpifschussup = false;
						players.get(1).helpifschussdown = true;
						players.get(1).helpifschussleft = false;
						players.get(1).helpifschussright = false;
					}
					if ((players.get(1).ifschuss == true)
							&& ((players.get(1).left == true) || (players.get(1).lefthilf == true))) {

						players.get(1).helpifschussup = false;
						players.get(1).helpifschussdown = false;
						players.get(1).helpifschussleft = true;
						players.get(1).helpifschussright = false;
					}
					if ((players.get(1).ifschuss == true)
							&& ((players.get(1).right == true) || (players.get(1).righthilf == true))) {

						players.get(1).helpifschussup = false;
						players.get(1).helpifschussdown = false;
						players.get(1).helpifschussleft = false;
						players.get(1).helpifschussright = true;
						
					}
				}
			}
		}
	
		

		
		// Wenn Taste losgelassen wird stehenbleiben
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode(); //TODO schleife mit .get(i) == 1 bzw. 2

			
			if ((key == KeyEvent.VK_RIGHT)) {
				players.get(0).right = false;
			}
			if ((key == KeyEvent.VK_LEFT)) {
				players.get(0).left = false;
			}
			if ((key == KeyEvent.VK_UP)) {
				players.get(0).up = false;
			}
			if ((key == KeyEvent.VK_DOWN)) {
				players.get(0).down = false;
			}

			if (players.size() == 2) {

				if ((key == KeyEvent.VK_D)) {
					players.get(1).right = false;
				}
				if ((key == KeyEvent.VK_A)) {
					players.get(1).left = false;
				}
				if ((key == KeyEvent.VK_W)) {
					players.get(1).up = false;
				}
				if ((key == KeyEvent.VK_S)) {
					players.get(1).down = false;
				}
			}

			
			if ((key == KeyEvent.VK_NUMPAD0)) {
				players.get(0).ifschuss = false;
			}
			if ((key == KeyEvent.VK_CONTROL)) {
				players.get(1).ifschuss = false;
			}

		}

	}

	public static void main(String argv[]) {
		new BoardT();
	}

	
	

	

}
