package utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author wangjigang
 *
 */
public class OkInputStreamBody extends RequestBody{
	private MediaType mediaType;
	private InputStream input;
	
	public OkInputStreamBody(InputStream input) {
		this.mediaType =  MediaType.parse("");
		this.input = input;
	}
	
	public OkInputStreamBody(InputStream input, MediaType mediaType) {
		this.mediaType = mediaType;
		this.input = input;
	}

	@Override
	public MediaType contentType() {
		return this.mediaType;
	}
	
	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		byte[] buf = new byte[1024];
		int n = 0;
		while((n=input.read(buf)) != -1){
			sink.write(buf, 0, n);
		}
	}

}
