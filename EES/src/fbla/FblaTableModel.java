/**
 * 
 */
package fbla;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Cody
 *
 */
@SuppressWarnings("serial")
public class FblaTableModel extends DefaultTableModel implements TableModel{

	String[] names;
	/**
	 * 
	 */
	public FblaTableModel(String[][] input, String[] collumns) {
		super(input, collumns);
		names = collumns;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setData(String[][] newData) {  
		this.setDataVector(newData, names);
		fireTableDataChanged();
		} 
}
