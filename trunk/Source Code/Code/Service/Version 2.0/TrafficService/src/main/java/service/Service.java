package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import json.FavoriteJSON;
import json.LocateJSON;
import json.ResultInput;
import utility.Constants;
import utility.GlobalValue;
import utility.Helper;
import utility.ResultInputCompare;
import dto.AccountDTO;
import dto.FavoriteDTO;
import dto.ReportDTO;
import dto.ResultDTO;
import dto.TrafficInfoDTO;
import dto.TrainImageDTO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.multipart.FormDataParam;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTDrawing;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTTwoCellAnchor;

import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.FavoriteDAO;
import dao.FavoriteDAOImpl;
import dao.ReportDAO;
import dao.ReportDAOImpl;
import dao.ResultDAO;
import dao.ResultDAOImpl;
import dao.TrafficInfoDAO;
import dao.TrafficInfoDAOImpl;
import dao.TrainImageDAO;
import dao.TrainImageDAOImpl;
import dto.CategoryDTO;
import static utility.GlobalValue.*;

@Path("/Service")
public class Service {


}
