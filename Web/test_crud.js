
function listerPays(){
    const selecteurPays = document.querySelector("#pays");
    const header = new Headers();
    header.set("Authorization",`Bearer ${localStorage.getItem("jwt")}`);
    fetch("http://localhost:8080/liste-pays",{ headers : header})
        .then((resultat) => resultat.json())
        .then((listePays) => {
            listePays.forEach((pays) => {
                const option = document.createElement("option");
                option.value = pays.id;
                option.innerHTML = pays.nom;
                selecteurPays.appendChild(option);
            });
        });
}


const baliseUL = document.getElementById("liste-utilisateur");

if (localStorage.getItem("jwt") != null && localStorage.getItem("jwt") !== "undefined") {
    toggleForm();
    listerPays();
    refresh();
}

function refresh() {
    baliseUL.innerHTML = "";

    const header = new Headers();
    header.set("Authorization",`Bearer ${localStorage.getItem("jwt")}`); // on ajoute le token dans l'en-tête

    fetch("http://localhost:8080/admin/utilisateurs",{ headers : header })
        .then(Response => Response.json())
        .then(utilisateur => {
            utilisateur.forEach((element) => {
                const li = document.createElement("li");
                li.innerHTML = `<span>${element.id} ${element.nom} ${element.prenom} <i> ${element.pays.nom} <i></span>`;
                const boutonSupprimer = document.createElement("button");
                boutonSupprimer.innerHTML = "Supprimer";
                li.appendChild(boutonSupprimer);
                boutonSupprimer.addEventListener("click", () => {
                    const header = new Headers();
                    header.set("Authorization",`Bearer ${localStorage.getItem("jwt")}`); // on ajoute le token dans l'en-tête
                    fetch(`http://localhost:8080/admin/utilisateur/${element.id}`, { method: "DELETE" , headers : header })
                        .then(resultat => {
                            refresh();
                        })
                })
                baliseUL.appendChild(li);
            })
        });
}

function addUtilisateur() {
    // récupérer les valeurs des inputs
    const nom = document.querySelector("input[name=nom]").value;
    const prenom = document.querySelector("input[name=prenom]").value;

    //on créait l'objet utilisateur
    const utilisateur = [{
        nom: nom,
        prenom: prenom,
        pays: { id: pays.value }
    }];

    const header = new Headers();
    header.set("Authorization",`Bearer ${localStorage.getItem("jwt")}`);
    header.set("Content-type","application/json");
    // on créait le JSON
    const json = JSON.stringify(utilisateur);
    fetch("http://localhost:8080/admin/utilisateur", {
        method: "POST",
        headers: header,
        body: json
    })
        .then(resultat => {
            if (resultat.ok) {
                refresh();
            }
        })

    console.log(json);

    return false;
}

function connection(){
    const utilisateur = {
        email : document.querySelector("#email").value,
        motDePasse : document.querySelector("#password").value
    };

    const json = JSON.stringify(utilisateur);
    const enTete = new Headers();
    enTete.set("Content-type","application/json");

    fetch("http://localhost:8080/connexion", {
        method:"POST",
        body : json,
        headers : enTete
    })
        .then(resultat => {
            if (resultat.ok) {
                return resultat.text();
            }else{
                alert("Erreur de connexion");
            }
        })
        .then((jwt)=>{
            localStorage.setItem("jwt",jwt);
            if (localStorage.getItem("jwt") != null && localStorage.getItem("jwt") !== "undefined") {
                listerPays();
                refresh();
                toggleForm();
            }
        })
    return false
}

function deconnection(){
    localStorage.removeItem("jwt");
    location.reload();
}

function toggleForm(){
    const elements = document.querySelectorAll(".hidden");
    elements.forEach((element)=>{
        element.classList.toggle("hidden");
    })
    const connection = document.querySelector("#connection");
    connection.classList.toggle("hidden");
}