package vo;

import java.io.Serializable;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

public class Category implements Serializable {
	private String id;
	private String catName;
	private String catImage;

	public String getCatImage() {
		return catImage;
	}

	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	//
	public void getCat(int catID) {
		final String SOAP_ACTION = "http://tempuri.org/getCat";
		final String METHOD_NAME = "getCat";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = "http://localhost:16781/Service1.svc";

		SoapObject table = null; // Cái này chứa table của dataset trả về thông
									// qua SoapObject
		SoapObject client = null; // Its the client pettition to the web
									// service(đoan này mình cũng chưa rõ)
		SoapObject tableRow = null; // Chứa hàng của bảng
		SoapObject responseBody = null; // Chứa nội dung XML của dataset
		AndroidHttpTransport transport = null; // cái này để gọi webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.addMapping(NAMESPACE, "Category", this.getClass());
		// chú ý nếu class(ở trên đầu í) ko phải là phim thì đổi
		sse.dotNet = true; // .NET thì chọn true
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				URL);
		//
		Category cat = new Category();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);

			// bước này lấy cả file XML
			responseBody = (SoapObject) sse.getResponse();
			// bước này bỏ các thông tin XML chỉ lấy kết quả trả về
			responseBody = (SoapObject) responseBody.getProperty(1);
			// Lấy thông tin XML của bảng trả về
			table = (SoapObject) responseBody.getProperty(0);
			// Ở đây lấy thông tin từng dòng của table, bạn chú ý 0 là dòng đầu
			// tiên
			tableRow = (SoapObject) table.getProperty(0);
			this.catName = tableRow.getProperty("catName").toString();
			this.id = tableRow.getProperty("catID").toString();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
