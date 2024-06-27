function enableEdit(schoolClassId) {
    let nameCell = document.getElementById('name-' + schoolClassId);
    let editButton = document.getElementById('edit-btn-' + schoolClassId);
    let saveButton = document.getElementById('save-btn-' + schoolClassId);

    nameCell.innerHTML = '<input form="' + schoolClassId + '-form-edit" type="text" name="name" id="edit-name-' + schoolClassId + '" value="' + nameCell.innerText + '" class="form-control"/>';

    editButton.style.display = 'none';
    saveButton.style.display = 'inline';
}

function saveEdit(schoolClassId) {
    document.getElementById(schoolClassId + '-form-edit').submit();
    let newName = document.getElementById('edit-name-' + schoolClassId).value;

    // Handle successful response
    let nameCell = document.getElementById('name-' + schoolClassId);
    let editButton = document.getElementById('edit-btn-' + schoolClassId);
    let saveButton = document.getElementById('save-btn-' + schoolClassId);

    nameCell.innerText = newName;
    saveButton.style.display = 'none';
    editButton.style.display = 'inline';

}

function hideAddButton() {
    document.getElementById('addNewSchoolClassButton').style.visibility = "hidden";
}

function showAddButton() {
    document.getElementById('addNewSchoolClassButton').style.visibility = "visible";
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
