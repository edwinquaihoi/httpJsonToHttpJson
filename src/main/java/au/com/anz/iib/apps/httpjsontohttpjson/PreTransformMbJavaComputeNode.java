package au.com.anz.iib.apps.httpjsontohttpjson;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

import au.com.anz.iib.commons.compute.CommonMbJavaComputeNode;
import au.com.anz.utils.TerminalAssemblyPair;

public class PreTransformMbJavaComputeNode extends CommonMbJavaComputeNode {

	@Override
	public TerminalAssemblyPair execute(MbMessageAssembly inAssembly) throws MbException {
		
		// 
		TerminalAssemblyPair terminalAssemblyPair = new TerminalAssemblyPair();
		
		// create new message as a copy of the input
		MbMessage outMessage = new MbMessage(inAssembly.getMessage());
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);
		
		terminalAssemblyPair.setMessageAssembly(outAssembly);
		terminalAssemblyPair.setOutputTerminal(getOutputTerminal("out"));

		return terminalAssemblyPair;
	}

}
