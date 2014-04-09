$(document).ready(function() {
    oTable = $('.dataTable').dataTable({
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "sDom": '<"F"f>t<""p>',
        "oLanguage": { 
        	"oPaginate": {
        		"sFirst":    "Đầu",
        		"sPrevious": "Trước",
        		"sNext":     "Sau",
        		"sLast":     "Cuối"
        	},
        	"sZeroRecords": "Không có dữ liệu",
        	"sEmptyTable": "Không có dữ liệu",
        	"sSearch":"Tìm kiếm"
        }
    });
    $("#select-type").select2('destroy'); 
} );