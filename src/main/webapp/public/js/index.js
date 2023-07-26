const image = document.querySelectorAll('.J_image')
const a = document.querySelectorAll('.boxa')
for (let i = 0;i<a.length;i++){
    a[i].addEventListener('mouseover', () => {
        if (image[i].src.indexOf('S.png') === -1){
            image[i].src = image[i].src.split('.png')[0] + 'S.png'
        }
    })
    function Amouseout(){
        if (this.firstElementChild.src.indexOf('S.png') !== -1){
            this.firstElementChild.src = this.firstElementChild.src.split('S.png')[0] + '.png'
        }
    }
    a[i].onmouseout = Amouseout
    a[i].onclick = function (){
        for (let j = 0; j < a.length; j++) {
            if (image[j].src.indexOf('S.png') !== -1){
                image[j].src = image[j].src.split('S.png')[0] + '.png'

            }
            a[j].onmouseout = Amouseout
        }
        image[i].src = image[i].src.split('.png')[0] + 'S.png'
        a[i].onmouseout = null
    }
}