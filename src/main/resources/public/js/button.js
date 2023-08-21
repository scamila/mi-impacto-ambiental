const input = document.getElementById("upload")
const text = document.getElementById("text")
const btn = document.getElementById("upload-btn")

input.addEventListener("change",() => {
    const path=input.value.split('\\')
    const filename=path[path.length-1]

    text.innerText = filename ? filename : "Choose file"
})