function enableEdit(educObjId) {
    let nameCell = document.getElementById('name-' + educObjId);
    let editButton = document.getElementById('edit-btn-' + educObjId);
    let saveButton = document.getElementById('save-btn-' + educObjId);

    //make the nameCell into an input text box
    nameCell.innerHTML = '<input form="' + educObjId + '-form-edit" type="text" name="name" id="edit-name-' + educObjId + '" value="' + nameCell.innerText + '" class="form-control"/>';

    //hide the edit button and show the save button
    editButton.style.display = 'none';
    saveButton.style.display = 'inline';
}

function saveEdit(educObjId) {
    //submit the edit
    document.getElementById(educObjId + '-form-edit').submit();
    //take the new value for the name
    let newName = document.getElementById('edit-name-' + educObjId).value;

    // Handle successful response
    let nameCell = document.getElementById('name-' + educObjId);
    let editButton = document.getElementById('edit-btn-' + educObjId);
    let saveButton = document.getElementById('save-btn-' + educObjId);

    //write the new value of the name in the nameCell  and hide the save button
    nameCell.innerText = newName;
    saveButton.style.display = 'none';
    editButton.style.display = 'inline';
}

function hideAddButton() {
    document.getElementById('addNewEducObjButton').style.visibility = "hidden";
}

function showAddButton() {
    document.getElementById('addNewEducObjButton').style.visibility = "visible";
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

function discardNewRow() {
    //hide the input text row and clear the contents written in it
    document.getElementById("newRow").style.display = "none";
    document.getElementById("new-name").value = "";
    showAddButton();
}