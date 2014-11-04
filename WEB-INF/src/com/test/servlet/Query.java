package com.test.servlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class Query extends HttpServlet {

	private static final long serialVersionUID = 4658051820337347890L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			resp.getWriter().write("ok");
			
			
			try {
				ServletFileUpload upload = new ServletFileUpload();

				// Parse the request
				FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
				    FileItemStream item = iter.next();
				    String name = item.getFieldName();
				    InputStream stream = item.openStream();
				    if (item.isFormField()) {
				        System.out.println("Form field " + name + " with value "
				            + Streams.asString(stream) + " detected.");
				    } else {
				        System.out.println("File field " + name + " with file name "
				            + item.getName() + " detected.");
				        // Process the input stream
				        saveFile(stream, item.getName());
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void saveFile(InputStream in, String name) throws FileNotFoundException {
		try {
			FileOutputStream out = new FileOutputStream(name);
			byte[] temp = new byte[1024*20];
			int len;
			int cur = 0;
			while((len = in.read(temp)) != -1) {
			    out.write(temp,0,len);
			    out.flush();
			    cur +=len;
			    System.out.println(name + " 已保存" + cur/1024 + "K");
			}
			out.flush();
			in.close();
			out.close();
			System.out.println(name + "保存完成");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
