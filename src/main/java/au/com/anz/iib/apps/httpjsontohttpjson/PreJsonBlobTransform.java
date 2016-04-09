package au.com.anz.iib.apps.httpjsontohttpjson;

import au.com.anz.iib.commons.compute.IBlobTransformer;

public class PreJsonBlobTransform implements IBlobTransformer {

	@Override
	public byte[] execute(byte[] input) {
		return input;
	}

}
