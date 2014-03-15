package dao;

import java.util.ArrayList;

import dto.ReportDTO;

public interface ReportDAO {
	public ArrayList<ReportDTO> listReport();
	public boolean add(ReportDTO reportDTO);
	public ArrayList<ReportDTO> searchReportByType(int type, Boolean isActive);
	public ReportDTO getDetailReport(int reportID);
	public boolean delete(ReportDTO ReportDTO);

}
