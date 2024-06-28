function enableEdit(teacherId) {
    let nameCell = document.getElementById('name-' + teacherId);
    let egnCell = document.getElementById('egn-' + teacherId);
    let editButton = document.getElementById('edit-btn-' + teacherId);
    let saveButton = document.getElementById('save-btn-' + teacherId);

    nameCell.innerHTML = '<input form="' + teacherId + '-form-edit" type="text" name="name" id="edit-name-' + teacherId + '" value="' + nameCell.innerText + '" class="form-control"/>';
    egnCell.innerHTML = '<input form="' + teacherId + '-form-edit" type="text" name="egn" id="edit-egn-' + teacherId + '" value="' + egnCell.innerText + '" class="form-control"/>';

    editButton.style.display = 'none';
    saveButton.style.display = 'inline';
}

function saveEdit(teacherId) {
    let form = document.getElementById(teacherId + '-form-edit');
    form.submit();
    let newName = document.getElementById('edit-name-' + teacherId).value;
    let newEgn = document.getElementById('edit-egn-' + teacherId).value;

    // Handle successful response
    let nameCell = document.getElementById('name-' + teacherId);
    let egnCell  = document.getElementById('egn-' + teacherId);
    let editButton = document.getElementById('edit-btn-' + teacherId);
    let saveButton = document.getElementById('save-btn-' + teacherId);

    nameCell.innerText = newName;
    egnCell.innerText = newEgn;
    saveButton.style.display = 'none';
    editButton.style.display = 'inline';

}

function hideAddButton() {
    document.getElementById('addNewTeacherButton').style.visibility = "hidden";
}

function showAddButton() {
    document.getElementById('addNewTeacherButton').style.visibility = "visible";
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
    document.getElementById("new-egn").value = "";
    showAddButton();
}
