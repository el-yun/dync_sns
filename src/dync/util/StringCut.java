package dync.util;

public class StringCut {
	public String result = "";

	public void nl2no() {
		result = result.replaceAll("<br>", " ");
	}
	public StringCut(String raw, int len, String encoding) {
		if (raw != null) {
			String[] ary = null;
			try {
				// raw 의 byte
				byte[] rawBytes = raw.getBytes(encoding);
				int rawLength = rawBytes.length;

				int index = 0;
				int minus_byte_num = 0;
				int offset = 0;

				int hangul_byte_num = encoding.equals("UTF-8") ? 3 : 2;

				if (rawLength > len) {
					int aryLength = (rawLength / len)
							+ (rawLength % len != 0 ? 1 : 0);
					ary = new String[aryLength];

					for (int i = 0; i < aryLength; i++) {
						minus_byte_num = 0;
						offset = len;
						if (index + offset > rawBytes.length) {
							offset = rawBytes.length - index;
						}
						for (int j = 0; j < offset; j++) {
							if (((int) rawBytes[index + j] & 0x80) != 0) {
								minus_byte_num++;
							}
						}
						if (minus_byte_num % hangul_byte_num != 0) {
							offset -= minus_byte_num % hangul_byte_num;
						}
						ary[i] = new String(rawBytes, index, offset, encoding);
						index += offset;

					}
				} else {
					ary = new String[] { raw };
				}
			} catch (Exception e) {

			}
			for (int i = 0; i < ary.length; i++) {
				result += ary[i];
			}
			result += "..";
		}
	}
}
