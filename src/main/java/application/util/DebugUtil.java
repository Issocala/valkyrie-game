package application.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DebugUtil {

	public static StringBuilder printStack() {
		Throwable t = new Throwable();
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement ste : t.getStackTrace()) {
			sb.append(ste).append("\n");
		}
		return sb;
	}

	public static String printTrack() {
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		StringBuilder sbf = new StringBuilder();
		for (StackTraceElement e : st) {
			if (sbf.length() > 0) {
				sbf.append(" <- ");
				sbf.append(System.getProperty("line.separator"));
			}
			sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}", e.getClassName(), e.getMethodName(), e.getLineNumber()));
		}
		return sbf.toString();
	}

	public static String printStack(Exception e) {
		StringWriter message = new StringWriter();
		PrintWriter writer = new PrintWriter(message);
		e.printStackTrace(writer);
		return message.toString();
	}

	public static StringBuilder printStack(Throwable t) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement ste : t.getStackTrace()) {
			sb.append(ste).append("\n");
		}
		return sb;
	}
}
