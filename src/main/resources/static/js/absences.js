// function updateStudentNames(selectElement) {
//     let selectedNumber = selectElement.value;
//     let studentNames = document.getElementById("studentNames");
//     studentNames.value = selectedNumber;
// }
//
// function updateStudentNumberInClass(selectElement) {
//     let selectedName = selectElement.value;
//     let studentNumber = document.getElementById("studentNumberInClass");
//     studentNumber.value = selectedName;
// }


// function enableEdit(absenceId) {
//     let studentNumberInClassCell = document.getElementById('studentNumberInClass-' + absenceId);
//     let absenceDateCell = document.getElementById('absenceDate-' + absenceId);
//     let educObjCell = document.getElementById('educObj.name-' + absenceId);
//     let absenceTypeCell = document.getElementById('absenceType-' + absenceId);
//     let statusCell = document.getElementById('status-' + absenceId);
//     let notesCell = document.getElementById('notes-' + absenceId);
//
//     let editButton = document.getElementById('edit-btn-' + absenceId);
//     let saveButton = document.getElementById('save-btn-' + absenceId);
//
//     // Copy the options from the create new inputs
//     let studentNumberOptions = document.getElementById('studentNumberInClass').innerHTML;
//     let educObjOptions = document.getElementById('educObjId').innerHTML;
//     let absenceTypeOptions = document.getElementById('absenceType').innerHTML;
//     let statusOptions = document.getElementById('status').innerHTML;
//
//     studentNumberInClassCell.innerHTML = '<select form="' + absenceId + '-form-edit" name="studentNumberInClass" id="edit-studentNumberInClass-' + absenceId + '" class="form-control">' + studentNumberOptions + '</select>';
//     absenceDateCell.innerHTML = '<input form="' + absenceId + '-form-edit" type="date" name="absenceDate" id="edit-absenceDate-' + absenceId + '" value="' + absenceDateCell.innerText + '" class="form-control"/>';
//     educObjCell.innerHTML = '<select form="' + absenceId + '-form-edit" name="educObj" id="edit-educObj-' + absenceId + '" class="form-control">' + educObjOptions + '</select>';
//     absenceTypeCell.innerHTML = '<select form="' + absenceId + '-form-edit" name="absenceType" id="edit-absenceType-' + absenceId + '" class="form-control">' + absenceTypeOptions + '</select>';
//     statusCell.innerHTML = '<select form="' + absenceId + '-form-edit" name="status" id="edit-status-' + absenceId + '" class="form-control">' + statusOptions + '</select>';
//     notesCell.innerHTML = '<input form="' + absenceId + '-form-edit" type="text" name="notes" id="edit-notes-' + absenceId + '" value="' + notesCell.innerText + '" class="form-control"/>';
//
//     // Hide the edit button and show the save button
//     editButton.style.display = 'none';
//     saveButton.style.display = 'inline';
// }

function enableEdit(absenceId) {
    let studentNumberInClassCell = document.getElementById('studentNumberInClass-' + absenceId);
    let absenceDateCell = document.getElementById('absenceDate-' + absenceId);
    let educObjCell = document.getElementById('educObj.name-' + absenceId);
    let absenceTypeCell = document.getElementById('absenceType-' + absenceId);
    let statusCell = document.getElementById('status-' + absenceId);
    let notesCell = document.getElementById('notes-' + absenceId);

    let editButton = document.getElementById('edit-btn-' + absenceId);
    let saveButton = document.getElementById('save-btn-' + absenceId);

    // Get current values
    let currentStudentNumberInClass = studentNumberInClassCell.innerText.split(' ')[0];
    let currentAbsenceDate = absenceDateCell.innerText.trim();
    let currentEducObj = educObjCell.innerText.trim();
    let currentAbsenceType = absenceTypeCell.innerText.trim();
    let currentStatus = statusCell.innerText.trim();
    let currentNotes = notesCell.innerText.trim();

    // Create and populate the dropdowns for edit mode
    let studentNumberOptions = document.getElementById('studentNumberInClass').innerHTML;
    let educObjOptions = document.getElementById('educObjId').innerHTML;
    let absenceTypeOptions = document.getElementById('absenceType').innerHTML;
    let statusOptions = document.getElementById('status').innerHTML;

    // Create select elements with the current value selected
    let studentNumberSelect = '<select form="' + absenceId + '-form-edit" name="studentNumberInClass" id="edit-studentNumberInClass-' + absenceId + '" class="form-control">' + studentNumberOptions.replace(new RegExp('value="' + currentStudentNumberInClass + '"'), 'value="' + currentStudentNumberInClass + '" selected') + '</select>';
    let educObjSelect = '<select form="' + absenceId + '-form-edit" name="educObjId" id="edit-educObj-' + absenceId + '" class="form-control">' + educObjOptions.replace(new RegExp('>' + currentEducObj + '</option>'), ' selected>' + currentEducObj + '</option>') + '</select>';
    let absenceTypeSelect = '<select form="' + absenceId + '-form-edit" name="absenceType" id="edit-absenceType-' + absenceId + '" class="form-control">' + absenceTypeOptions.replace(new RegExp('>' + currentAbsenceType + '</option>'), ' selected>' + currentAbsenceType + '</option>') + '</select>';
    let statusSelect = '<select form="' + absenceId + '-form-edit" name="status" id="edit-status-' + absenceId + '" class="form-control">' + statusOptions.replace(new RegExp('>' + currentStatus + '</option>'), ' selected>' + currentStatus + '</option>') + '</select>';

    studentNumberInClassCell.innerHTML = studentNumberSelect;
    absenceDateCell.innerHTML = '<input form="' + absenceId + '-form-edit" type="date" name="absenceDate" id="edit-absenceDate-' + absenceId + '" value="' + currentAbsenceDate + '" class="form-control"/>';
    educObjCell.innerHTML = educObjSelect;
    absenceTypeCell.innerHTML = absenceTypeSelect;
    statusCell.innerHTML = statusSelect;
    notesCell.innerHTML = '<input form="' + absenceId + '-form-edit" type="text" name="notes" id="edit-notes-' + absenceId + '" value="' + currentNotes + '" class="form-control"/>';

    // Hide the edit button and show the save button
    editButton.style.display = 'none';
    saveButton.style.display = 'inline';
}


// function saveEdit(absenceId) {
//     // Submit the form to save the changes
//     document.getElementById(absenceId + '-form-edit').submit();
//
//     // Retrieve the new values for the fields
//     let newStudentNumberInClass = document.getElementById('edit-studentNumberInClass-' + absenceId).value;
//     let newAbsenceDate = document.getElementById('edit-absenceDate-' + absenceId).value;
//     let newEducObj = document.getElementById('edit-educObj-' + absenceId).selectedOptions[0].text;
//     let newAbsenceType = document.getElementById('edit-absenceType-' + absenceId).selectedOptions[0].text;
//     let newStatus = document.getElementById('edit-status-' + absenceId).selectedOptions[0].text;
//     let newNotes = document.getElementById('edit-notes-' + absenceId).value;
//
//     // Handle successful response (updating the table cell values)
//     let studentNumberInClassCell = document.getElementById('studentNumberInClass-' + absenceId);
//     let absenceDateCell = document.getElementById('absenceDate-' + absenceId);
//     let educObjCell = document.getElementById('educObj-' + absenceId);
//     let absenceTypeCell = document.getElementById('absenceType-' + absenceId);
//     let statusCell = document.getElementById('status-' + absenceId);
//     let notesCell = document.getElementById('notes-' + absenceId);
//
//     let editButton = document.getElementById('edit-btn-' + absenceId);
//     let saveButton = document.getElementById('save-btn-' + absenceId);
//
//     // Update the table cells with the new values
//     studentNumberInClassCell.innerText = newStudentNumberInClass;
//     absenceDateCell.innerText = newAbsenceDate;
//     educObjCell.innerText = newEducObj;
//     absenceTypeCell.innerText = newAbsenceType;
//     statusCell.innerText = newStatus;
//     notesCell.innerText = newNotes;
//
//     // Hide the save button and show the edit button
//     saveButton.style.display = 'none';
//     editButton.style.display = 'inline';
// }
function saveEdit(absenceId) {
    // Submit the form to save the changes
    document.getElementById(absenceId + '-form-edit').submit();

    // Retrieve the new values for the fields
    let newStudentNumberInClass = document.getElementById('edit-studentNumberInClass-' + absenceId).value;
    let newAbsenceDate = document.getElementById('edit-absenceDate-' + absenceId).value;
    let newEducObj = document.getElementById('edit-educObj.name-' + absenceId).selectedOptions[0].text;
    let newAbsenceType = document.getElementById('edit-absenceType-' + absenceId).selectedOptions[0].text;
    let newStatus = document.getElementById('edit-status-' + absenceId).selectedOptions[0].text;
    let newNotes = document.getElementById('edit-notes-' + absenceId).value;

    // Handle successful response (updating the table cell values)
    let studentNumberInClassCell = document.getElementById('studentNumberInClass-' + absenceId);
    let absenceDateCell = document.getElementById('absenceDate-' + absenceId);
    let educObjCell = document.getElementById('educObj-' + absenceId);
    let absenceTypeCell = document.getElementById('absenceType-' + absenceId);
    let statusCell = document.getElementById('status-' + absenceId);
    let notesCell = document.getElementById('notes-' + absenceId);

    let editButton = document.getElementById('edit-btn-' + absenceId);
    let saveButton = document.getElementById('save-btn-' + absenceId);

    // Update the table cells with the new values
    studentNumberInClassCell.innerText = newStudentNumberInClass;
    absenceDateCell.innerText = newAbsenceDate;
    educObjCell.innerText = newEducObj;
    absenceTypeCell.innerText = newAbsenceType;
    statusCell.innerText = newStatus;
    notesCell.innerText = newNotes;

    // Hide the save button and show the edit button
    saveButton.style.display = 'none';
    editButton.style.display = 'inline';
}



function hideAddButton() {
    document.getElementById('addNewAbsenceButton').style.visibility = "hidden";
}

function showAddButton() {
    document.getElementById('addNewAbsenceButton').style.visibility = "visible";
}

function addNewRow() {

    hideAddButton();

    //show the input text row and focus on it
    document.getElementById("newRow").style.display = "";
    document.getElementById("studentNumberInClass").focus();
}

function submitNew() {
    document.getElementById('new-form-create').submit();
    //showAddButton();//?
}

function discardNewRow() {
    // Hide the new row
    let newRow = document.getElementById("newRow");
    newRow.style.display = "none";

    // Clear each input individually
    document.getElementById("studentNumberInClass").selectedIndex = 0;
    document.getElementById("absenceDate").value = "";
    document.getElementById("educObjId").selectedIndex = 0;
    document.getElementById("absenceType").selectedIndex = 0;
    document.getElementById("status").selectedIndex = 0;
    document.getElementById("absenceNotes").value = "";

    // Show the add button
    showAddButton();
}