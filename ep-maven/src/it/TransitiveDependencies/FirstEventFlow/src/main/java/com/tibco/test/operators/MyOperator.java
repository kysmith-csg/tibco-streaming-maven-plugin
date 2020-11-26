package com.tibco.test.operators;

import com.streambase.sb.*;
import com.streambase.sb.operator.*;
import com.tibco.ep.dtm.metrics.api.IMetric;

/**
 * Generated by JDT StreamBase Client Templates (Version: 11.0.0.2011101810).
 *
 * This class is used as a Java Operator in a StreamBase application.
 * One instance will be created for each Java Operator in a StreamBase 
 * application. 
 * <p>
 * Enqueue methods should only be called from processTuple.
 * @see Parameterizable
 * @see Operator
 * For in-depth information on implementing a custom Java Operator, please see
 * "Developing StreamBase Java Operators" in the StreamBase documentation.
 */
public class MyOperator extends Operator implements Parameterizable {

	public static final long serialVersionUID = 1606215876026L;
	// Local variables
	private int inputPorts = 1;
	private int outputPorts = 1;
	private int nextOutputPort = 0;
	private Schema[] outputSchemas; // caches the Schemas given during init() for use at processTuple()

	/**
	* The constructor is called when the Operator instance is created, but before the Operator 
	* is connected to the StreamBase application. We recommended that you set the initial input
	* port and output port count in the constructor by calling setPortHints(inPortCount, outPortCount).
	* The default is 1 input port, 1 output port. The constructor may also set default values for 
	* operator parameters. These values will be displayed in StreamBase Studio when a new instance
	* of this operator is  dragged to the canvas, and serve as the default values for omitted
	* optional parameters.
	 */
	public MyOperator() {
		super();
		setPortHints(inputPorts, outputPorts);
		setDisplayName(this.getClass().getSimpleName());
		setShortDisplayName(this.getClass().getSimpleName());
		
		new MyJavaClass();
	}

	/**
	* The typecheck method is called after the Operator instance is connected in the StreamBase
	* application, allowing the Operator to validate its properties. The Operator class may 
	* change the number of input or output ports by calling the requireInputPortCount(portCount)
	* method or the setOutputSchema(schema, portNum) method. If the verifyInputPortCount method 
	* is passed a different number of ports than the Operator currently has, a PortMismatchException
	* (subtype of TypecheckException) is thrown.
	*/
	public void typecheck() throws TypecheckException {
		// typecheck: require a specific number of input ports
		requireInputPortCount(inputPorts);

		// TODO Ensure that all properties have valid values, and typecheck the input schemas here

		// you must specify a setOutputSchema for each port, so that StreamBase Studio
		// knows how many output ports to draw for the output ports. If you do not
		// use setOutputSchema for each port, the Java Operator on the StreamBase Studio
		// canvas will not display the correct number of output ports.
		for (int i = 0; i < outputPorts; ++i) {
			// TODO this example sets every output port Schema the same as the first input port; change this as needed
			setOutputSchema(i, getInputSchema(0));
		}

	}

	/**
	* This method will be called by the StreamBase server for each Tuple given
	* to the Operator to process. This is the only time an operator should 
	* enqueue output Tuples.
	* @param inputPort the input port that the tuple is from (ports are zero based)
	* @param tuple the tuple from the given input port
	* @throws StreamBaseException Terminates the application.
	*/
	public void processTuple(int inputPort, Tuple tuple) throws StreamBaseException {
		if (getLogger().isInfoEnabled()) {
			getLogger().info("operator processing a tuple at input port" + inputPort);
		}
		// TODO only the first input port is processed; see the template code for typecheck()
		if (inputPort > 0) {
			getLogger().info("operator skipping tuple at input port" + inputPort);
			return;
		}

		// create a new output tuple from the Schema at the port we are about to send to
		Tuple out = outputSchemas[inputPort].createTuple();

		// TODO this template just copies each field value from input port 0 (the first input port)
		for (int i = 0; i < out.getSchema().getFieldCount(); ++i) {
			// note: best performance is achieved retrieving values through Tuple#getField(Schema.Field)
			out.setField(i, tuple.getField(i));
		}

		// nextOutputPort is used to send tuples by round-robin on every output port by this template.
		sendOutput(nextOutputPort, out);
		nextOutputPort = (nextOutputPort + 1) % outputPorts;
	}

	/**
	 * If typecheck succeeds, the init method is called before the StreamBase application
	 * is started. Note that your Operator class is not required to define the init method,
	 * unless (for example) you need to perform initialization of a resource such as a JDBC
	 * pool, if your operator is making JDBC calls. StreamBase Studio does not call this
	 * during authoring.
	 */
	public void init() throws StreamBaseException {
		super.init();
		// for best performance, consider caching input or output Schema.Field objects for
		// use later in processTuple()
		outputSchemas = new Schema[outputPorts];

		for (int i = 0; i < outputPorts; ++i) {
			outputSchemas[i] = getRuntimeOutputSchema(i);
		}
	}

	/**
	*  The shutdown method is called when the StreamBase server is in the process of shutting down.
	*/
	public void shutdown() {

	}

}
