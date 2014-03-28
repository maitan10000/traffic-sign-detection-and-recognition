package dao;

import java.util.ArrayList;

import dto.ReportDTO;

public interface ReportDAO {	
	public boolean add(ReportDTO reportDTO);	
	public ReportDTO getReportDetail(int reportID);
	public boolean delete(int reportID);
	public ArrayList<ReportDTO> searchReportByType(int type);
	public ArrayList<ReportDTO> searchReportByType(int type, Boolean isActive);


}
