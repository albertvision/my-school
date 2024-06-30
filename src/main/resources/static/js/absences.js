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
//     let nameCell = document.getElementById('name-' + absenceId);
//     let editButton = document.getElementById('edit-btn-' + absenceId);
//     let saveButton = document.getElementById('save-btn-' + absenceId);
//
//     //make the nameCell into an input text box
//     nameCell.innerHTML = '<input form="' + absenceId + '-form-edit" type="text" name="name" id="edit-name-' + absenceId + '" value="' + nameCell.innerText + '" class="form-control"/>';
//
//     //hide the edit button and show the save button
//     editButton.style.display = 'none';
//     saveButton.style.display = 'inline';
// }
//
// function saveEdit(absenceId) {
//     //submit the edit
//     document.getElementById(absenceId + '-form-edit').submit();
//     //take the new value for the name
//     let newName = document.getElementById('edit-name-' + absenceId).value;
//
//     // Handle successful response
//     let nameCell = document.getElementById('name-' + absenceId);
//     let editButton = document.getElementById('edit-btn-' + absenceId);
//     let saveButton = document.getElementById('save-btn-' + absenceId);
//
//     //write the new value of the name in the nameCell  and hide the save button
//     nameCell.innerText = newName;
//     saveButton.style.display = 'none';
//     editButton.style.display = 'inline';
// }

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
    document.getElementById("new-name").focus();
}

function submitNew() {
    document.getElementById('new-form-create').submit();
    //showAddButton();//?
}

// function discardNewRow() {
//     //hide the input text row and clear the contents written in it
//     document.getElementById("newRow").style.display = "none";
//     document.getElementById("new-name").value = "";
//     showAddButton();
// }