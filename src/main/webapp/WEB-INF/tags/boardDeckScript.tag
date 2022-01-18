<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>

    // CARDS

    let cards = document.querySelectorAll(".card-item-selectable");

    cards.forEach((card) =>{
        card.addEventListener("change", (e) => {
            console.log(e);
            let checkbox = e.target;
            console.log(checkbox);
            let card = checkbox.parentElement;
            if(checkbox.checked) {
                card.classList.add("card-item-checked");
            }
            else{
                card.classList.remove("card-item-checked");
            }
        });
    });

</script>