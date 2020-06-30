/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: Ismail Hakki Yesil
*/
package supsimjava;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SimulationFrame {
	JFrame secondFrame,frame;
	JPanel panel,secondPanel;
	JOptionPane messagePanel;
	JTextField maxSimuTimeTxt, maxArrTimeTxt, maxServTimeTxt;
	JButton startButton;
	JLabel maxSimulationTime, maxServiceTime, maxArrivalTime, timerSpeedLabel;
	JLabel simulationTime, simulationTimeValue, queueLength, queueLenghtValue, totalServiceTime, totalServiceTimeValue;
	JComboBox<String> timerSpeed;
	static int maxSimulation, maxArrival, maxService, timeSpend, seconds;
	static CustomerQueue queue;
	static Cashier cashier;
	static boolean secondAdded;
	Customer cashingCustomer = null, newCustomer, firstCustomer;
	boolean customerRemoved = true;
	boolean cashierBusy = false;
	static int nextArrival;
	public SimulationFrame() {
		firstWindow();
	}

	private void firstWindow() {
		final String cBoxList[] = {"5",  "200", "400", "500", "1000" };

		// Initializing the components
		frame = new JFrame();
		panel = new JPanel();
		messagePanel = new JOptionPane();
		maxSimulationTime = new JLabel("Max Simulation Time: ");
		maxArrivalTime = new JLabel("Max Arrival Time: ");
		maxServiceTime = new JLabel("Max Service Time: ");
		timerSpeedLabel = new JLabel("Timer Speed (Milliseconds)");
		maxSimuTimeTxt = new JTextField(20);
		maxArrTimeTxt = new JTextField(20);
		maxServTimeTxt = new JTextField(20);
		startButton = new JButton();
		startButton.setText("Start Simulation");
		timerSpeed = new JComboBox<>(cBoxList);

		// Initializing the Panel
		panel.setBorder(BorderFactory.createEmptyBorder(70, 100, 70, 100));
		panel.setLayout(new GridLayout(5, 2));

		//Adding necessary components to The Panel
		panel.add(maxSimulationTime);
		panel.add(maxSimuTimeTxt);
		panel.add(maxArrivalTime);
		panel.add(maxArrTimeTxt);
		panel.add(maxServiceTime);
		panel.add(maxServTimeTxt);
		panel.add(timerSpeedLabel);
		panel.add(timerSpeed);
		panel.add(startButton);

		// Adding Panel to Frame
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("COMP:132 HW2");
		frame.pack();
		frame.setVisible(true);
		frame.setSize(600, 400);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				try {
					maxSimulation = Integer.parseInt(maxSimuTimeTxt.getText());
					maxArrival = Integer.parseInt(maxArrTimeTxt.getText());
					maxService = Integer.parseInt(maxServTimeTxt.getText());
					timeSpend = Integer.parseInt(cBoxList[timerSpeed.getSelectedIndex()]);
					frame.dispose();
					secondWindow();
				} catch (final NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "You didn't enter valid information");
				}
			}
		});
	}
	private void secondWindow() {

		secondFrame = new JFrame();
		secondPanel = new JPanel();

		simulationTime = new JLabel("Current Simulation Time:");
		simulationTimeValue = new JLabel();
		queueLength = new JLabel("Current Queue Lenght: ");
		queueLenghtValue = new JLabel();
		totalServiceTime = new JLabel("Total Service Time: ");
		totalServiceTimeValue = new JLabel();

		secondPanel.setBorder(BorderFactory.createEmptyBorder(70, 100, 70, 100));
		secondPanel.setLayout(new GridLayout(3, 2));

		secondPanel.add(simulationTime);
		secondPanel.add(simulationTimeValue);
		secondPanel.add(queueLength);
		secondPanel.add(queueLenghtValue);
		secondPanel.add(totalServiceTime);
		secondPanel.add(totalServiceTimeValue);

		secondFrame.add(secondPanel, BorderLayout.CENTER);
		secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		secondFrame.pack();
		secondFrame.setSize(600, 400);
		secondFrame.setVisible(true);

		timerStarter(simulationTimeValue, queueLenghtValue, totalServiceTimeValue);
	}
	private void timerStarter(JLabel label, JLabel queueVal , JLabel totSerTimeVal) {
		cashier = new Cashier();
		queue = new CustomerQueue();
		firstCustomer = CreateCustomer();
		queue.enqueue(firstCustomer);
		nextArrival = firstCustomer.arrivalTime;
		customerRemoved = true;
		cashierBusy = false;
		ActionListener counter = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (seconds >= maxSimulation) {
					for (Customer customer : CustomerQueue.customerQueue)
						queue.totalWaitTime += customer.waitTime;
					System.out.println("Average Wait Time: " + queue.AverageWaitTime() + "Total Wait Time: " + queue.totalWaitTime);
					String stats = "Number of Customers: " + Cashier.totalCustomer + "\nAverage Wait Time: "
							+ queue.AverageWaitTime() + "\nAverage Serve Time: " + cashier.GetAverageServiceTime()
							+ "\nMaximum Wait Time: " + queue.maxWaitTime + "\nMaximum Queue Length: " + queue.maxCustomerLine;
					secondFrame.dispose();
					JOptionPane.showMessageDialog(null, stats);
					System.exit(0);
				} else {
					label.setText("" + seconds);
					seconds++;
					int size = CustomerQueue.customerQueue.size();
					queueVal.setText("" + size);
					totSerTimeVal.setText("" + Cashier.totalServiceTime);
					// ----------------------MAIN LOOP----------------------
					System.out.println("Current Second: " + seconds);
					System.out.println("-------------------------");
					if (seconds >= nextArrival) {
						newCustomer = CreateCustomer();
						queue.enqueue(newCustomer);
						nextArrival = newCustomer.arrivalTime;
					}
					if (!cashierBusy && !CustomerQueue.customerQueue.isEmpty()) {

						firstCustomer = CustomerQueue.customerQueue.getFirst();
						if (firstCustomer.isArrived(seconds)) {
							cashierBusy = true;
							cashingCustomer = firstCustomer;
						}
					}
					if (cashierBusy && !CustomerQueue.customerQueue.isEmpty()) {
						cashier.Serve(cashingCustomer);
						if (cashier.serviceFinished)
							cashierBusy = false;
						else
							cashierBusy = true;
						customerRemoved = false;
					}
					if (!cashierBusy && !customerRemoved) {
						queue.dequeue();
						customerRemoved = true;
					}
					queue.increaseQueueWaitTime();
				}
			}
		};
		Timer timer = new Timer(timeSpend, counter);
		timer.setInitialDelay(0);
		timer.start();
		seconds = 0;
	}
	public static int GetArrival() {
		Random rnd = new Random();
		return rnd.nextInt(maxArrival) + 1;
	}

	public static int GetServiceTime() {
		Random random = new Random();
		return random.nextInt(maxService) + 1 ;
	}

	public static Customer CreateCustomer() {
		Customer customer = new Customer(GetArrival() + seconds);
		customer.serviceTime = GetServiceTime();
		Cashier.totalServiceTime += customer.serviceTime;
		Cashier.totalCustomer += 1;
		System.out.println("Customer Added: " + customer + " Queue size: " + CustomerQueue.customerQueue.size());
		return customer;
	}
}