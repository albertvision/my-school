function enableEdit(typeMarkId) {
    let nameCell = document.getElementById('name-' + typeMarkId);
    let editButton = document.getElementById('edit-btn-' + typeMarkId);
    let saveButton = document.getElementById('save-btn-' + typeMarkId);

    nameCell.innerHTML = '<input form="' + typeMarkId + '-form-edit" type="text" name="name" id="edit-name-' + typeMarkId + '" value="' + nameCell.innerText + '" class="form-control"/>';

    editButton.style.display = 'none';
    saveButton.style.display = 'inline';
}

function saveEdit(typeMarkId) {
    document.getElementById(typeMarkId + '-form-edit').submit();
    let newName = document.getElementById('edit-name-' + typeMarkId).value;

    // Handle successful response
    let nameCell = document.getElementById('name-' + typeMarkId);
    let editButton = document.getElementById('edit-btn-' + typeMarkId);
    let saveButton = document.getElementById('save-btn-' + typeMarkId);

    nameCell.innerText = newName;
    saveButton.style.display = 'none';
    editButton.style.display = 'inline';

}

function hideAddButton() {
    document.getElementById('addNewTypeMarkButton').style.visibility = "hidden";
}

function showAddButton() {
    document.getElementById('addNewTypeMarkButton').style.visibility = "visible";
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
