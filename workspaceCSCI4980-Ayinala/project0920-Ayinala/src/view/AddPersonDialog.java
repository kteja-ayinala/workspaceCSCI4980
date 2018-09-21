package view;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import model.MyPerson;

public class AddPersonDialog extends TitleAreaDialog {
	private Text text1;
	private Text text2;
	private Text text3;
	private Text text4;
	private MyPerson person;

	public MyPerson getPerson() {
		return person;
	}

	public AddPersonDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Add a new Person");
		setMessage("Please enter the data of the new person", IMessageProvider.INFORMATION);
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parent.setLayout(layout);
		Label label1 = new Label(parent, SWT.NONE);
		label1.setText("First Name");
		text1 = new Text(parent, SWT.BORDER);
		Label label2 = new Label(parent, SWT.NONE);
		label2.setText("Last Name");
		text2 = new Text(parent, SWT.BORDER);
		Label label3 = new Label(parent, SWT.NONE);
		label3.setText("Phone");
		text3 = new Text(parent, SWT.BORDER);
		Label label4 = new Label(parent, SWT.NONE);
		label4.setText("Address");
		text4 = new Text(parent, SWT.BORDER);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.horizontalSpan = 2;
		return parent;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;

		Button button = new Button(parent, SWT.PUSH);
		button.setText("OK");
		button.setFont(JFaceResources.getDialogFont());
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (text1.getText().length() != 0 && text2.getText().length() != 0 && text3.getText().length() != 0
						&& text4.getText().length() != 0) {
					person = new MyPerson(text1.getText(), text2.getText(), text3.getText(), text4.getText());
					close();
				} else {
					setErrorMessage("Please enter all data");
				}
			}
		});
	}
}
