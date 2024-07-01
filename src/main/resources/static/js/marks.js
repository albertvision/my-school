function enableEdit(markId) {
    let studentNumberInClassCell = document.getElementById('studentNumberInClass-' + markId);
    let markDateTimeCell = document.getElementById('markDateTime-' + markId);
    let educObjCell = document.getElementById('educObj.name-' + markId);
    let markValueCell = document.getElementById('markValue-' + markId);
    let typeMarkCell = document.getElementById('typeMark-' + markId);
    let statusCell = document.getElementById('status-' + markId);

    let editButton = document.getElementById('edit-btn-' + markId);
    let saveButton = document.getElementById('save-btn-' + markId);

    // Get current values
    let currentStudentNumberInClass = studentNumberInClassCell.innerText.split(' - ')[0];
    let currentMarkDateTime = markDateTimeCell.innerText.trim();
    let currentEducObj = educObjCell.innerText.trim();
    let currentMarkValue = markValueCell.innerText.trim();
    let currentTypeMark = typeMarkCell.innerText.trim();
    let currentStatus = statusCell.innerText.trim();

    // Create and populate the dropdowns for edit mode
    let studentNumberOptions = document.getElementById('studentNumberInClass').innerHTML;
    let educObjOptions = document.getElementById('educObjId').innerHTML;
    let typeMarkOptions = document.getElementById('typeMark').innerHTML;
    let statusOptions = document.getElementById('status').innerHTML;

    // Create select elements with the current value selected
    let studentNumberSelect = '<select form="' + markId + '-form-edit" name="studentNumberInClass" id="edit-studentNumberInClass-' + markId + '" class="form-control">' + studentNumberOptions.replace(new RegExp('value="' + currentStudentNumberInClass + '"'), 'value="' + currentStudentNumberInClass + '" selected') + '</select>';
    let educObjSelect = '<select form="' + markId + '-form-edit" name="educObjId" id="edit-educObjId-' + markId + '" class="form-control">' + educObjOptions.replace(new RegExp('>' + currentEducObj + '</option>'), ' selected>' + currentEducObj + '</option>') + '</select>';
    let markValueInput = '<input form="' + markId + '-form-edit" type="number" name="value" id="edit-markValue-' + markId + '" value="' + currentMarkValue + '" class="form-control" min="2" max="6" step="0.5"/>';
    let typeMarkSelect = '<select form="' + markId + '-form-edit" name="typeMark" id="edit-typeMark-' + markId + '" class="form-control">' + typeMarkOptions.replace(new RegExp('>' + currentTypeMark + '</option>'), ' selected>' + currentTypeMark + '</option>') + '</select>';
    let statusSelect = '<select form="' +markId + '-form-edit" name="status" id="edit-status-' + markId + '" class="form-control">' + statusOptions.replace(new RegExp('>' + currentStatus + '</option>'), ' selected>' + currentStatus + '</option>') + '</select>';

    studentNumberInClassCell.innerHTML = studentNumberSelect;
    markDateTimeCell.innerHTML = '<input form="' + markId + '-form-edit" type="datetime-local" name="dateTime" id="edit-markDateTime-' + markId + '" value="' + currentMarkDateTime + '" class="form-control"/>';

    educObjCell.innerHTML = educObjSelect;
    markValueCell.innerHTML = markValueInput;
    typeMarkCell.innerHTML = typeMarkSelect;
    statusCell.innerHTML = statusSelect;

    // Hide the edit button and show the save button
    editButton.style.display = 'none';
    saveButton.style.display = 'inline';
}

function saveEdit(markId) {
    // Submit the form to save the changes
    document.getElementById(markId + '-form-edit').submit();

    // Retrieve the new values for the fields
    let newStudentNumberInClass = document.getElementById('edit-studentNumberInClass-' + markId).value;
    let newEducObjId = document.getElementById('edit-educObjId-' + markId).value;
    let newDate = document.getElementById('edit-markDateTime-' + markId).value;
    let newValue = document.getElementById('edit-markValue-' + markId).value;
    let newTypeMark = document.getElementById('edit-typeMark-' + markId).selectedOptions[0].text;
    let newStatus = document.getElementById('edit-status-' + markId).selectedOptions[0].text;

    // Handle successful response (updating the table cell values)
    let studentNumberInClassCell = document.getElementById('studentNumberInClass-' + markId);
    let markDateTimeCell = document.getElementById('markDateTime-' + markId);
    let educObjCell = document.getElementById('educObj.name-' + markId);
    let markValueCell = document.getElementById('markValue-' + markId);
    let typeMarkCell = document.getElementById('typeMark-' + markId);
    let statusCell = document.getElementById('status-' + markId);

    let editButton = document.getElementById('edit-btn-' + markId);
    let saveButton = document.getElementById('save-btn-' + markId);

    // Update the table cells with the new values
    studentNumberInClassCell.innerText = newStudentNumberInClass + ' - ' + document.getElementById('edit-studentNumberInClass-' + markId).selectedOptions[0].text;
    markDateTimeCell.innerText = newDate;
    educObjCell.innerText = newEducObjId + ' - ' + document.getElementById('edit-educObjId-' + markId).selectedOptions[0].text;
    markValueCell.innerText = newValue;
    typeMarkCell.innerText = newTypeMark;
    statusCell.innerText = newStatus;

    // Hide the save button and show the edit button
    saveButton.style.display = 'none';
    editButton.style.display = 'inline';
}

function hideAddButton() {
    document.getElementById('addNewMarkButton').style.visibility = "hidden";
}

function showAddButton() {
    document.getElementById('addNewMarkButton').style.visibility = "visible";
}

function addNewRow() {
    hideAddButton();
    document.getElementById("newRow").style.display = "";
    document.getElementById("studentNumberInClass").focus();
}

function submitNew() {
    document.getElementById('new-form-create').submit();
}

function discardNewRow() {
    let newRow = document.getElementById("newRow");
    newRow.style.display = "none";

    // Clear each input individually
    document.getElementById("studentNumberInClass").selectedIndex = 0;
    document.getElementById("educObjId").selectedIndex = 0;
    document.getElementById("dateTime").value = "";
    document.getElementById("value").value = "";
    document.getElementById("typeMark").selectedIndex = 0;
    document.getElementById("status").selectedIndex = 0;

    // Show the add button
    showAddButton();
}
