$(document).ready( function () {
	 var table = $('#reservationsTable').DataTable({
			"sAjaxSource": "/svc/reservations/list",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "id" },
				  { "mData": "deviceSerial" },
				  { "mData": "startTime" },
				  { "mData": "endTime" },
				  { "mData": "userId" },
				  { "mData": "ip" }
			]
	 })
});