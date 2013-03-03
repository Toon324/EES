package fbla;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * An implementation of TableModel that allows data to be updated. Disallows
 * direct editting of table contents.
 * 
 * @author Cody Swendrowski
 */
@SuppressWarnings("serial")
public class FblaTableModel extends DefaultTableModel implements TableModel {

	private String[] names;

	/**
	 * Creates a new FblaTableModel with given input data and column names.
	 * 
	 * @param input
	 *            Data to put into table
	 * @param collumns
	 *            Names of columns
	 */
	public FblaTableModel(String[][] input, String[] columns) {
		super(input, columns);
		names = columns;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets data to newData, then updates Table.
	 * 
	 * @param newData
	 *            Data to change to
	 */
	public void setData(String[][] newData) {
		this.setDataVector(newData, names);
		fireTableDataChanged();
	}
}
