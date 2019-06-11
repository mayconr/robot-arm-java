/*
 * Created by JFormDesigner on Sat Jun 01 14:37:44 BRT 2019
 */

package com.github.mayconr.garra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicSliderUI;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.*;
import org.jdesktop.swingbinding.SwingBindings;

import com.fazecast.jSerialComm.SerialPort;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.FormLayout;

public class GuarraUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public GuarraUI() {
		initComponents();
	}

	private void sair(ActionEvent e) {
		if (model.getPorta() != null && model.isConectado()) {
			desconectar(e);
		}
		dispose();
	}

	private void createUIComponents() {
		this.model = new GuarraModel();
		this.model.setPortas(
				Arrays.stream(SerialPort.getCommPorts()).map(PortaAdapter::new).collect(Collectors.toList()));
		this.sliderArticulacaoA = new JSlider();
		this.sliderArticulacaoA.addMouseListener(new JumpClick());
		this.sliderArticulacaoB = new JSlider();
		this.sliderArticulacaoB.addMouseListener(new JumpClick());
		this.sliderArticulacaoC = new JSlider();
		this.sliderArticulacaoC.addMouseListener(new JumpClick());
		this.sliderArticulacaoD = new JSlider();
		this.sliderArticulacaoD.addMouseListener(new JumpClick());
		this.sliderArticulacaoE = new JSlider();
		this.sliderArticulacaoE.addMouseListener(new JumpClick());
		this.table1 = new JTable();
		this.table1.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("DELETE"), "DELETAR");
		this.table1.getActionMap().put("DELETAR", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getComando() != null) {
					removeComando();
				}
			}
		});
	}

	private void conectar(ActionEvent e) {
		final SerialPort port = model.getPorta();
		port.setBaudRate(9600);
		model.setConectado(port.openPort());
		sliderArticulacaoA.setValue(50);
		sliderArticulacaoB.setValue(50);
		sliderArticulacaoC.setValue(50);
		sliderArticulacaoD.setValue(50);
		sliderArticulacaoE.setValue(50);
		new Thread(() -> {
			try {
				while (true) {
					while (port.bytesAvailable() == 0)
						Thread.sleep(20);

					byte[] readBuffer = new byte[port.bytesAvailable()];
					port.readBytes(readBuffer, readBuffer.length);
					System.out.println(new String(readBuffer, Charset.forName("UTF8")));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start();
	}

	private void desconectar(ActionEvent e) {
		model.setConectado(model.getPorta().closePort());
	}

	private void articulacaoA(ChangeEvent e) {
		extractSlideValue(e, value->{
			final String command = "A:" + value;
    		model.getPorta().writeBytes(command.getBytes(), command.length());
		});
	}

	private void articulacaoB(ChangeEvent e) {
		extractSlideValue(e, value->{
			final String command = "B:" + value;
    		model.getPorta().writeBytes(command.getBytes(), command.length());
		});
	}

	private void articulacaoC(ChangeEvent e) {
		extractSlideValue(e, value->{
			final String command = "C:" + value;
    		model.getPorta().writeBytes(command.getBytes(), command.length());
		});
	}

	private void articulacaoD(ChangeEvent e) {
		extractSlideValue(e, value->{
			final String command = "D:" + value;
    		model.getPorta().writeBytes(command.getBytes(), command.length());
		});
	}

	private void articulacaoE(ChangeEvent e) {
		extractSlideValue(e, value->{
			final String command = "E:" + value;
    		model.getPorta().writeBytes(command.getBytes(), command.length());
		});
	}

	private void extractSlideValue(ChangeEvent event, IntConsumer consumer) {
		JSlider source = (JSlider)event.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int)source.getValue();
            consumer.accept(fps);
        }
	}

	private void registrarComando() {
		final StringBuilder builder = new StringBuilder();
		builder.append("A:")
		.append(sliderArticulacaoA.getValue())
		.append(";")
		.append("B:")
		.append(sliderArticulacaoB.getValue())
		.append(";")
		.append("C:")
		.append(sliderArticulacaoC.getValue())
		.append(";")
		.append("D:")
		.append(sliderArticulacaoD.getValue())
		.append(";")
		.append("E:")
		.append(sliderArticulacaoE.getValue())
		.append(";");
		
		String nome = JOptionPane.showInputDialog("Informe o nome");
		if (!nome.trim().isEmpty()) {
			model.addComando(new Comando(nome, builder.toString()));
		}
	}

	private void removeComando() {
		model.getComandos().remove(model.getComando());
	}
	
	private void executarComando() {
		String comando = model.getComando().getValor();
		model.getPorta().writeBytes(comando.getBytes(), comando.length());
	}

	private class JumpClick extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			JSlider sourceSlider=(JSlider)e.getSource();
			if (sourceSlider.isEnabled()) {
				BasicSliderUI ui = (BasicSliderUI)sourceSlider.getUI();
				int value = ui.valueForXPosition( e.getX() );
				sourceSlider.setValue(value);
			}
		}
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		createUIComponents();

		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		panel1 = new JPanel();
		button1 = new JButton();
		panel2 = new JPanel();
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		button2 = new JButton();
		button3 = new JButton();
		label2 = new JLabel();
		panel3 = new JPanel();
		label3 = new JLabel();
		label18 = new JLabel();
		panel4 = new JPanel();
		separator1 = compFactory.createSeparator("Comandos", SwingConstants.CENTER);
		scrollPane1 = new JScrollPane();
		button5 = new JButton();
		label9 = new JLabel();
		label8 = new JLabel();
		label19 = new JLabel();
		label4 = new JLabel();
		label11 = new JLabel();
		label10 = new JLabel();
		label20 = new JLabel();
		label5 = new JLabel();
		label12 = new JLabel();
		label13 = new JLabel();
		label21 = new JLabel();
		label6 = new JLabel();
		label14 = new JLabel();
		label15 = new JLabel();
		label22 = new JLabel();
		label7 = new JLabel();
		label16 = new JLabel();
		label17 = new JLabel();
		label23 = new JLabel();
		button4 = new JButton();
		portaAdapterConverter1 = new PortaAdapterConverter();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout());

			//---- button1 ----
			button1.setText("Sair");
			button1.addActionListener(e -> sair(e));
			panel1.add(button1);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FormLayout(
				"5*(default, $lcgap), default:grow, $lcgap, default",
				"2*(default, $lgap), default:grow, $lgap, default"));

			//---- label1 ----
			label1.setText("Porta");
			panel2.add(label1, CC.xy(3, 3));
			panel2.add(comboBox1, CC.xy(5, 3));

			//---- button2 ----
			button2.setText("Connectar");
			button2.addActionListener(e -> conectar(e));
			panel2.add(button2, CC.xy(7, 3));

			//---- button3 ----
			button3.setText("Desconectar");
			button3.addActionListener(e -> desconectar(e));
			panel2.add(button3, CC.xy(9, 3));

			//---- label2 ----
			label2.setText("Conex\u00e3o realizada com sucesso");
			label2.setForeground(new Color(18, 117, 18));
			panel2.add(label2, CC.xy(11, 3));

			//======== panel3 ========
			{
				panel3.setBorder(new EtchedBorder());
				panel3.setLayout(new FormLayout(
					"$rgap, 5*(default, $lcgap), default:grow, $rgap",
					"$rgap, 11*(default, $lgap), default:grow, $rgap"));

				//---- label3 ----
				label3.setText("Articula\u00e7\u00e3o A");
				panel3.add(label3, CC.xywh(4, 2, 5, 1, CC.CENTER, CC.DEFAULT));

				//---- label18 ----
				label18.setText("Valor");
				panel3.add(label18, CC.xy(10, 2, CC.CENTER, CC.DEFAULT));

				//---- sliderArticulacaoA ----
				sliderArticulacaoA.setPaintLabels(true);
				sliderArticulacaoA.setPaintTicks(true);
				sliderArticulacaoA.setSnapToTicks(true);
				sliderArticulacaoA.addChangeListener(e -> articulacaoA(e));
				panel3.add(sliderArticulacaoA, CC.xy(6, 4));

				//======== panel4 ========
				{
					panel4.setBorder(new BevelBorder(BevelBorder.RAISED));
					panel4.setForeground(Color.gray);
					panel4.setLayout(new FormLayout(
						"$rgap, default:grow, $rgap",
						"$rgap, default, $lgap, default:grow, $rgap, default, $rgap"));
					panel4.add(separator1, CC.xy(2, 2));

					//======== scrollPane1 ========
					{

						//---- table1 ----
						table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						scrollPane1.setViewportView(table1);
					}
					panel4.add(scrollPane1, CC.xy(2, 4, CC.FILL, CC.FILL));

					//---- button5 ----
					button5.setText("Executar Comando");
					button5.addActionListener(e -> executarComando());
					panel4.add(button5, CC.xy(2, 6));
				}
				panel3.add(panel4, CC.xywh(12, 2, 1, 23));

				//---- label9 ----
				label9.setText("ABRE");
				label9.setForeground(Color.gray);
				panel3.add(label9, CC.xy(4, 4, CC.CENTER, CC.DEFAULT));

				//---- label8 ----
				label8.setText("FECHA");
				label8.setForeground(Color.gray);
				panel3.add(label8, CC.xy(8, 4, CC.CENTER, CC.DEFAULT));

				//---- label19 ----
				label19.setText("text");
				panel3.add(label19, CC.xy(10, 4, CC.CENTER, CC.DEFAULT));

				//---- label4 ----
				label4.setText("Articula\u00e7\u00e3o B");
				panel3.add(label4, CC.xywh(4, 6, 5, 1, CC.CENTER, CC.DEFAULT));

				//---- label11 ----
				label11.setText("SOBE");
				label11.setForeground(Color.gray);
				panel3.add(label11, CC.xy(4, 8, CC.CENTER, CC.DEFAULT));

				//---- sliderArticulacaoB ----
				sliderArticulacaoB.addChangeListener(e -> articulacaoB(e));
				panel3.add(sliderArticulacaoB, CC.xy(6, 8));

				//---- label10 ----
				label10.setText("DESCE");
				label10.setForeground(Color.gray);
				panel3.add(label10, CC.xy(8, 8, CC.CENTER, CC.DEFAULT));

				//---- label20 ----
				label20.setText("text");
				panel3.add(label20, CC.xy(10, 8, CC.CENTER, CC.DEFAULT));

				//---- label5 ----
				label5.setText("Articula\u00e7\u00e3o C");
				panel3.add(label5, CC.xywh(4, 10, 5, 1, CC.CENTER, CC.DEFAULT));

				//---- label12 ----
				label12.setText("SOBE");
				label12.setForeground(Color.gray);
				panel3.add(label12, CC.xy(4, 12, CC.CENTER, CC.DEFAULT));

				//---- sliderArticulacaoC ----
				sliderArticulacaoC.addChangeListener(e -> articulacaoC(e));
				panel3.add(sliderArticulacaoC, CC.xy(6, 12));

				//---- label13 ----
				label13.setText("DESCE");
				label13.setForeground(Color.gray);
				panel3.add(label13, CC.xy(8, 12, CC.CENTER, CC.DEFAULT));

				//---- label21 ----
				label21.setText("text");
				panel3.add(label21, CC.xy(10, 12, CC.CENTER, CC.DEFAULT));

				//---- label6 ----
				label6.setText("Articula\u00e7\u00e3o D");
				panel3.add(label6, CC.xywh(4, 14, 5, 1, CC.CENTER, CC.DEFAULT));

				//---- label14 ----
				label14.setText("SOBE");
				label14.setForeground(Color.gray);
				panel3.add(label14, CC.xy(4, 16, CC.CENTER, CC.DEFAULT));

				//---- sliderArticulacaoD ----
				sliderArticulacaoD.addChangeListener(e -> articulacaoD(e));
				panel3.add(sliderArticulacaoD, CC.xy(6, 16));

				//---- label15 ----
				label15.setText("DESCE");
				label15.setForeground(Color.gray);
				panel3.add(label15, CC.xy(8, 16, CC.CENTER, CC.DEFAULT));

				//---- label22 ----
				label22.setText("text");
				panel3.add(label22, CC.xy(10, 16, CC.CENTER, CC.DEFAULT));

				//---- label7 ----
				label7.setText("Articula\u00e7\u00e3o E");
				panel3.add(label7, CC.xywh(4, 18, 5, 1, CC.CENTER, CC.DEFAULT));

				//---- label16 ----
				label16.setText("ESQUERDA");
				label16.setForeground(Color.gray);
				panel3.add(label16, CC.xy(4, 20, CC.CENTER, CC.DEFAULT));

				//---- sliderArticulacaoE ----
				sliderArticulacaoE.addChangeListener(e -> articulacaoE(e));
				panel3.add(sliderArticulacaoE, CC.xy(6, 20));

				//---- label17 ----
				label17.setText("DIREITA");
				label17.setForeground(Color.gray);
				panel3.add(label17, CC.xy(8, 20, CC.CENTER, CC.DEFAULT));

				//---- label23 ----
				label23.setText("text");
				panel3.add(label23, CC.xy(10, 20, CC.CENTER, CC.DEFAULT));

				//---- button4 ----
				button4.setText("Registrar");
				button4.addActionListener(e -> registrarComando());
				panel3.add(button4, CC.xy(10, 22));
			}
			panel2.add(panel3, CC.xywh(3, 5, 9, 1, CC.FILL, CC.FILL));
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(620, 440);
		setLocationRelativeTo(getOwner());

		//---- bindings ----
		bindingGroup = new BindingGroup();
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			model, ELProperty.create("${porta!=null && !conectado}"),
			button2, BeanProperty.create("enabled")));
		bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE,
			model, (BeanProperty) BeanProperty.create("portas"), comboBox1));
		{
			Binding binding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
				model, BeanProperty.create("porta"),
				comboBox1, BeanProperty.create("selectedItem"));
			binding.setConverter(portaAdapterConverter1);
			bindingGroup.addBinding(binding);
		}
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			model, BeanProperty.create("conectado"),
			label2, BeanProperty.create("visible")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
			model, BeanProperty.create("conectado"),
			button3, BeanProperty.create("enabled")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
			model, BeanProperty.create("conectado"),
			sliderArticulacaoA, BeanProperty.create("enabled")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
			model, BeanProperty.create("conectado"),
			sliderArticulacaoB, BeanProperty.create("enabled")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			model, BeanProperty.create("conectado"),
			sliderArticulacaoC, BeanProperty.create("enabled")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
			model, BeanProperty.create("conectado"),
			sliderArticulacaoD, BeanProperty.create("enabled")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
			model, BeanProperty.create("conectado"),
			sliderArticulacaoE, BeanProperty.create("enabled")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			sliderArticulacaoA, BeanProperty.create("value"),
			label19, BeanProperty.create("text")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			sliderArticulacaoB, BeanProperty.create("value"),
			label20, BeanProperty.create("text")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			sliderArticulacaoC, BeanProperty.create("value"),
			label21, BeanProperty.create("text")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			sliderArticulacaoD, BeanProperty.create("value"),
			label22, BeanProperty.create("text")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			sliderArticulacaoE, BeanProperty.create("value"),
			label23, BeanProperty.create("text")));
		{
			JTableBinding binding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE,
				model, (BeanProperty) BeanProperty.create("comandos"), table1);
			binding.setEditable(false);
			binding.addColumnBinding(BeanProperty.create("nome"))
				.setColumnName("Nome")
				.setColumnClass(String.class);
			binding.addColumnBinding(BeanProperty.create("valor"))
				.setColumnName("Comando")
				.setColumnClass(String.class);
			bindingGroup.addBinding(binding);
		}
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
			model, ELProperty.create("${comando!=null}"),
			button5, BeanProperty.create("enabled")));
		bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
			model, BeanProperty.create("comando"),
			table1, BeanProperty.create("selectedElement")));
		bindingGroup.bind();
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
	private JPanel panel1;
	private JButton button1;
	private JPanel panel2;
	private JLabel label1;
	private JComboBox comboBox1;
	private JButton button2;
	private JButton button3;
	private JLabel label2;
	private JPanel panel3;
	private JLabel label3;
	private JLabel label18;
	private JSlider sliderArticulacaoA;
	private JPanel panel4;
	private JComponent separator1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton button5;
	private JLabel label9;
	private JLabel label8;
	private JLabel label19;
	private JLabel label4;
	private JLabel label11;
	private JSlider sliderArticulacaoB;
	private JLabel label10;
	private JLabel label20;
	private JLabel label5;
	private JLabel label12;
	private JSlider sliderArticulacaoC;
	private JLabel label13;
	private JLabel label21;
	private JLabel label6;
	private JLabel label14;
	private JSlider sliderArticulacaoD;
	private JLabel label15;
	private JLabel label22;
	private JLabel label7;
	private JLabel label16;
	private JSlider sliderArticulacaoE;
	private JLabel label17;
	private JLabel label23;
	private JButton button4;
	private GuarraModel model;
	private PortaAdapterConverter portaAdapterConverter1;
	private BindingGroup bindingGroup;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
