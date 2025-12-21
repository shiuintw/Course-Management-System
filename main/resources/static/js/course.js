document.addEventListener("DOMContentLoaded", () => {
    const table = document.getElementById("course");
    const rows = table.querySelectorAll("tbody tr");
    const fetches = [];

    rows.forEach(row => {
        const courseId = row.dataset.courseId;
        const statusTd = row.querySelector(".status");

        if (!courseId) return;

        const p = fetch(`/course/take/${courseId}`)
            .then(res => {
                if (!res.ok) throw new Error("Network response was not ok");
                return res.json();
            })
            .then(data => {
                if (data && data.taken) {
                    statusTd.textContent = "taken";
                } else {
                    renderTakeButton(statusTd, courseId);
                }
            })
            .catch(err => {
                console.error("Error checking course status:", err);
                statusTd.textContent = "Error";
            });

        fetches.push(p);
    });

    Promise.all(fetches).then(() => table.style.display = "");
});

function renderTakeButton(container, courseId) {
    const btn = document.createElement("button");
    btn.textContent = "Take";
    btn.onclick = () => takeCourse(courseId, container);
    container.innerHTML = "";
    container.appendChild(btn);
}

function takeCourse(courseId, statusTd) {
    const btn = statusTd.querySelector("button");
    if(btn) btn.disabled = true;

    fetch(`/course/take/${courseId}`, { method: "POST" })
        .then(res => {
            if (res.ok) {
                statusTd.textContent = "taken";
            } else if (res.status === 401) {
                alert("please login");
                window.location.href = "/login";
            } else {
                throw new Error("Failed to take course");
            }
        })
        .catch(err => {
            console.error(err);
            alert("fail");
            if(btn) btn.disabled = false;
        });
}