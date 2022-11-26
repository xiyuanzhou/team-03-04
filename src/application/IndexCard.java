package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class IndexCard {
	private SimpleStringProperty index_notes;
	private CheckBox checkbox;
	private SimpleStringProperty status;
	
	public IndexCard(String status) {
		this.status = new SimpleStringProperty(status);;
	}
	
	public IndexCard(String index_notes, CheckBox checkbox, String status) {
		this.index_notes = new SimpleStringProperty(index_notes);
		this.checkbox = checkbox;
		this.status = new SimpleStringProperty(status);
	}

	public String getIndex_notes() {
		return index_notes.get();
	}
	
	public String getStatus() {
		return status.get();
	}
	
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}
	
	public void setIndex_notes(SimpleStringProperty index_notes) {
		this.index_notes = index_notes;
	}

	public CheckBox getCheckbox() {
		return checkbox;
	}
	
	public void setCheckbox(CheckBox checkbox) {
		this.checkbox = checkbox;
	}
	
	
	
}
