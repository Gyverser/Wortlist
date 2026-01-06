document.addEventListener("DOMContentLoaded", () => {

    const modal = document.getElementById("wordModal");
    const addBtn = document.getElementById("addWordBtn");
    const cancelBtn = document.getElementById("cancelBtn");
    const saveBtn = document.getElementById("saveBtn");

    const wordInput = document.getElementById("wordInput");
    const meaningInput = document.getElementById("meaningInput");
    const sentenceInput = document.getElementById("sentenceInput");
    const levelInput = document.getElementById("levelInput");

    const errorBox = document.getElementById("errorBox");
    const modalTitle = document.getElementById("modalTitle");

    let editingId = null;

    const isAdmin = document.querySelector(".edit-btn") !== null;

    function openModal() {
        modal.classList.add("modal-open");
    }

    function closeModal() {
        modal.classList.remove("modal-open");
        errorBox.classList.add("hidden");
        errorBox.innerHTML = "";
        editingId = null;
    }

    function resetForm() {
        wordInput.value = "";
        meaningInput.value = "";
        sentenceInput.value = "";
        levelInput.value = "A1";
    }

    if (addBtn && isAdmin) {
        addBtn.addEventListener("click", () => {
            resetForm();
            modalTitle.textContent = "Add Word";
            openModal();
        });
    }

    cancelBtn.addEventListener("click", closeModal);

    if (isAdmin) {
        saveBtn.addEventListener("click", async () => {

            const payload = {
                word: wordInput.value,
                meaning: meaningInput.value,
                sentence: sentenceInput.value,
                level: levelInput.value
            };

            const url = editingId ? `/api/words/${editingId}` : "/api/words";
            const method = editingId ? "PUT" : "POST";

            try {
                const response = await fetch(url, {
                    method,
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(payload)
                });

                if (!response.ok) {
                    if (response.status === 401 || response.status === 403) {
                        errorBox.innerHTML = "You must be logged in as admin to perform this action";
                        errorBox.classList.remove("hidden");
                        return;
                    }
                    const data = await response.json();
                    errorBox.innerHTML = data.message || "Validation error";
                    errorBox.classList.remove("hidden");
                    return;
                }

                window.location.reload();
            } catch (error) {
                errorBox.innerHTML = "Network error occurred";
                errorBox.classList.remove("hidden");
            }
        });

        document.querySelectorAll(".delete-btn").forEach(btn => {
            btn.addEventListener("click", async () => {
                const id = btn.closest("tr").dataset.id;
                if (!confirm("Delete this word?")) return;

                try {
                    const response = await fetch(`/api/words/${id}`, { method: "DELETE" });
                    if (!response.ok) {
                        if (response.status === 401 || response.status === 403) {
                            alert("You must be logged in as admin to delete words");
                        } else {
                            alert("Failed to delete word");
                        }
                        return;
                    }
                    window.location.reload();
                } catch (error) {
                    alert("Network error occurred");
                }
            });
        });

        document.querySelectorAll(".edit-btn").forEach(btn => {
            btn.addEventListener("click", () => {
                const row = btn.closest("tr");
                editingId = row.dataset.id;

                wordInput.value = row.children[0].textContent;
                meaningInput.value = row.children[1].textContent;
                sentenceInput.value = row.children[2].textContent;
                levelInput.value = row.children[3].innerText.trim();

                modalTitle.textContent = "Edit Word";
                openModal();
            });
        });
    }

});
