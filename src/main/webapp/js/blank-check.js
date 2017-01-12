function value_compare_row_check(val, compare_val, alert_content) {
	if (val < compare_val) {
		alert(alert_content);
		return false;
	}
}
/**
 * 값 공백 체크
 * @param val
 * @param alert_content
 * @returns {Boolean}
 */
function value_blank_check(val, alert_content) {
	if (val == "") {
		alert(alert_content);
		return false;
	}
}
/**
 * 입력 공백 체크
 * @param input_id
 * @param alert_content
 * @returns {Boolean}
 */
function input_blank_check(input_id, alert_content) {
	if ($("#"+input_id).val() == "") {
		jAlert(alert_content, input_id);
		//$("#"+input_id).focus();
		return false;
	} 
}

/**
 * 셀렉트 박스 선택 체크 
 * @param selectbox_id
 * @param alert_content
 * @returns {Boolean}
 */
function selectbox_blank_check(selectbox_id, alert_content) {
	if ($("#"+selectbox_id+" option:selected").val() == "") {
		jAlert(alert_content, selectbox_id);
		//$("#"+selectbox_id).focus();
		return false;
	}
}