package programs.MyNotes;

import java.util.ArrayList;

import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import essentials.frameworks.javaFX.FXApplication;
import essentials.tools.containers.fx.FXFrame;
import essentials.tools.containers.fx.components.FXButton;

public class MyNotes extends FXApplication {

	private FXButton save, add;

	private TabPane tabPane;
	private ArrayList<Tab> tabs;
	private ArrayList<TextArea> textAreas;

	private int numOfTabs = 1;

	public MyNotes() {
		width = 300;
		height = width / 4 * 5;

		frame = new FXFrame();
		panel = new JFXPanel();

		save = new FXButton();
		add = new FXButton();

		tabPane = new TabPane();
		tabs = new ArrayList<Tab>();
		textAreas = new ArrayList<TextArea>();

		tabs.add(new Tab("note" + numOfTabs));
		textAreas.add(new TextArea());
		textAreas.get(numOfTabs - 1).setMinSize(width, height);
		textAreas.get(numOfTabs - 1).setMaxSize(width, height);
		tabs.get(numOfTabs - 1).setContent(textAreas.get(numOfTabs - 1));
	}

	public void run() {
		addEffects();
	}

	public void addEffects() {
		add.setVisible(true);
		save.setVisible(true);

		//add.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("res//plus-icon.png"))));
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				numOfTabs++;
				tabs.add(new Tab("note " + (numOfTabs)));

				tabs.get(numOfTabs - 1).setOnClosed(new EventHandler<Event>() {
					public void handle(Event event) {
						numOfTabs--;
						textAreas.remove(numOfTabs);
					}
				});

				textAreas.add(new TextArea());
				tabs.get(numOfTabs - 1).setContent(textAreas.get(numOfTabs - 1));
				textAreas.get(numOfTabs - 1).setMinSize(width, height);
				textAreas.get(numOfTabs - 1).setMaxSize(width, height);
				textAreas.get(numOfTabs - 1).setWrapText(true);
				tabPane.getTabs().add(tabs.get(numOfTabs - 1));
			}
		});

		final DropShadow shadow = new DropShadow();
		add.addShadow(shadow);
		add.setText("+");
		add.setLocation(width - (width / 15 * 2), 5);
		add.setStyle("-fx-background-color: deepskyblue;");

		save.addShadow(shadow);
		save.setText("save");
		save.setLocation(width - (width / 10 * 3), 5);
		save.setStyle("-fx-background-color: #33ff66;");

		tabPane.setVisible(true);
		tabPane.setMinWidth(width);
		tabPane.getTabs().addAll(tabs);

		frame.addNode(tabPane);
		frame.addNode(add);
		frame.addNode(save);
	}

	public static void main(String[] args) {
		MyNotes main = new MyNotes();
		main.frame.initAndShow(main.panel, main.width, main.height);
		main.run();
	}
}
