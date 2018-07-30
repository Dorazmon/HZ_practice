import React, { Component } from 'react';
import PropTypes from 'prop-types';
import TweenOne, { TweenOneGroup } from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';


class Content extends Component {

  static propTypes = {
    id: PropTypes.string,
  };

  static defaultProps = {
    className: 'content4',
  };

  componentWillMount(){
    // console.log("11111");
  }
  componentDidMount(){
   
    // myAddEvent(window,'mouseover',checkclass);
    // function checkclass(){
    //     let scrollobj = document.getElementsByClassName('img-list')[0];
    //     let i = 0;
    //     if(scrollobj !== undefined) {console.log(scrollobj);scrollImg();i=1;}
    //     else {console.log("noscrollobj");
    //       if(i !== 0){

    //       }
    //     }
    // }
    // function scrollImg(){
    //   function next(){
    //     if(i<aPicList.length){
    //       new ScrollImg(aPicList[i]);
    //       window.timer = setTimeout(next, 0);
    //     }
    //     i++;
    //     // if(i==aPicList.length){
    //     //   clearTimeout();
    //     // }
    //   }
      
    //   const aPicList=getByClass(document,'img-list');
    //   let i=0;
    //   if(aPicList.length!==0){next();console.log("11222");}
    //   else{console.log("2222");}
    // }
  }
  
  getChildrenToRender = (item, i) => {
    const id = `block${i}`;
    return (<li
      key={i}
      id={`${this.props.id}-${id}`}
    >
      <div className="content-wrapper">
        <span><img src={item.img1} height="100%" /></span>
        <a href={item.src1} target="_blank" rel="noopener noreferrer"><p>{item.content1}</p></a>
      </div>
      <div className="content-wrapper">
        <span><img src={item.img2} height="100%" /></span>
        <a href={item.src2} target="_blank" rel="noopener noreferrer"><p>{item.content2}</p></a>
      </div>
    </li>);
  }
  
  getEnterAnim = (e, isMode) => {
    const index = e.index;
    const delay = isMode ? index * 50 + 200 : index % 4 * 100 + Math.floor(index / 4) * 100 + 300;
    return { y: '+=30', opacity: 0, type: 'from', delay };
  };

  scroll=(e) => {
    // console.log(e);
    if (e.key === "14" && e.type === "appear") {
      $(function(){
        $("#scrolllist1").scrollForever();
      });
    }

  }
  // imgscroll = () => {
  //   myAddEvent(window,'load',scrollImg);
  //   function scrollImg(){
  //     function next(){

  //       if(i<aPicList.length){
  //         new ScrollImg(aPicList[i]);
  //         window.timer = setTimeout(next, 0);
  //       }
  //       i++;
  //       if(i==aPicList.length){
  //         clearTimeout();
  //       }
  //     }
  //     const aPicList=getByClass(document,'pic-list');
  //     let i=0;
  //     next();
  //   }
  // }

  render() {
    const props = { ...this.props };
    const isMode = props.isMode;
    delete props.isMode;
    const dataArray = [
      { img1: 'static/user/chuanhua.png', content1: '传化集团', src1: 'http://www.etransfar.com/', img2: 'static/user/h10.png', content2: '甬易支付', src2: 'http://www.yoyipay.com/' },
      { img1: 'static/user/h2.png', content1: '海尔集团', src1: 'http://www.haier.com/cn/', img2: 'static/user/h11.png', content2: '统统付', src2: 'https://www.sumpay.cn/' },
      { img1: 'static/user/h5.png', content1: '海康威视', src1: 'http://www.hikvision.com/', img2: 'static/user/h22.png', content2: '爱学贷', src2: 'http://www.aiyoumi.com/' },
      { img1: 'static/user/h4.png', content1: '连连支付', src1: 'http://www.lianlianpay.com/', img2: 'static/user/h28.png', content2: '复星集团', src2: 'https://www.fosun.com/' },
      { img1: 'static/user/h3.png', content1: '鑫合汇', src1: 'https://www.xinhehui.com/', img2: 'static/user/h9.png', content2: '生意宝', src2: 'http://cn.toocle.com/' },
      { img1: 'static/user/h6.png', content1: '浙江物产', src1: 'http://www.zjmi.com.cn', img2: 'static/user/h13.png', content2: '贝付', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h7.png', content1: '浙江省农业科学院', src1: 'http://www.zaas.ac.cn/', img2: 'static/user/h14.png', content2: '点点搜财', src2: 'https://www.ddsoucai.com/' },
      { img1: 'static/user/h8.png', content1: '网易', src1: 'http://www.163.com/', img2: 'static/user/h15.png', content2: '义支付', src2: 'www.yiwupay.com' },
      { img1: 'static/user/h12.png', content1: '东海商品', src1: 'http://www.esce.cn/', img2: 'static/user/h16.png', content2: '九游', src2: 'http://www.9game.cn/' },
      { img1: 'static/user/h29.png', content1: '中国联通', src1: 'http://www.chinaunicom.com.cn/', img2: 'static/user/h17.png', content2: '车空间', src2: 'http://www.zcckj.com/' },
      { img1: 'static/user/h25.png', content1: '小拇指', src1: 'http://www.xiaomuzhi.com/', img2: 'static/user/h18.png', content2: '泰一指尚', src2: 'http://www.typsm.com/' },
      { img1: 'static/user/h27.png', content1: '什么值得买', src1: 'http://www.smzdm.com/', img2: 'static/user/h19.png', content2: '融都科技', src2: 'http://www.erongdu.com/' },
      { img1: 'static/user/h30.png', content1: '海融易', src1: 'https://www.hairongyi.com/', img2: 'static/user/h20.png', content2: '慕课', src2: 'http://www.imooc.com/' },
      { img1: 'static/user/h26.png', content1: '永达汽车', src1: 'http://www.ydauto.com.cn/', img2: 'static/user/h21.png', content2: '乾付宝', src2: 'https://qfb.ehangtian.com/' },
      { img1: 'static/user/h24.png', content1: '鑫翔七迅', src1: 'http://www.star7.cn/', img2: 'static/user/h23.png', content2: '丝芭传媒', src2: 'http://www.snh48.com/' },
    ];
    // const dataArray1 = [
    //   { img: 'static/user/chuanhua.png', content: '传化集团', src: 'http://www.etransfar.com/' },
    //   { img: 'static/user/h2.png', content: '海尔集团', src: 'http://www.haier.com/cn/' },
    //   { img: 'static/user/h5.png', content: '海康威视', src: 'http://www.hikvision.com/' },
    //   { img: 'static/user/h4.png', content: '连连支付', src: 'http://www.lianlianpay.com/' },
    //   { img: 'static/user/h3.png', content: '鑫合汇', src: 'https://www.xinhehui.com/' },
    //   { img: 'static/user/h6.png', content: '浙江物产', src: 'http://www.zjmi.com.cn' },
    //   { img: 'static/user/h7.png', content: '浙江省农业科学院', src: 'http://www.zaas.ac.cn/' },
    //   { img: 'static/user/h8.png', content: '更多', src: 'services/web-penetration' },
    //   { img: 'static/user/h2.png', content: '海尔集团', src: 'http://www.haier.com/cn/' },
    //   { img: 'static/user/h5.png', content: '海康威视', src: 'http://www.hikvision.com/' },
    //   { img: 'static/user/h4.png', content: '连连支付', src: 'http://www.lianlianpay.com/' },
    //   { img: 'static/user/h3.png', content: '鑫合汇', src: 'https://www.xinhehui.com/' },
    //   { img: 'static/user/h6.png', content: '浙江物产', src: 'http://www.zjmi.com.cn' },
    //   { img: 'static/user/h7.png', content: '浙江省农业科学院', src: 'http://www.zaas.ac.cn/' },
    //   { img: 'static/user/h8.png', content: '更多', src: 'services/web-penetration' },
    // ];
    const childrenToRender = dataArray.map(this.getChildrenToRender);
    // const childrenToRender1 = dataArray1.map(this.getChildrenToRender);
    return (
      <div
        {...props}
        className="content-template-wrapper content4-wrapper"
      >
        <OverPack
          className={`content-template ${props.className}`}
        >
          <TweenOne
            animation={{ y: '+=30', opacity: 0, type: 'from', ease: 'easeOutQuad' }}
            // component="h1"
            key="h1"
            reverseDelay={300}
            id={`${props.id}-title`}
            // onChange={this.scroll.bind(this)}
          >
            <h1>我们的用户</h1>
            <p>Our Users</p>
          </TweenOne>
          <div className="img-list" id="scrolllist1">
          <TweenOneGroup
            className={`${props.className}-img-wrapper`}
            component="ul"
            key="ul"
            enter={(e) => {
              return (this.getEnterAnim(e, isMode))
            }}
            leave={{ y: '+=30', opacity: 0, ease: 'easeOutQuad' }}
            id={`${props.id}-ul`}
            onEnd={this.scroll.bind(this)}
          >
            {childrenToRender}
          </TweenOneGroup>
          </div>
          {/* <div className="pic-list">
          <TweenOneGroup
            className={`${props.className}-img-wrapper`}
            component="ul"
            key="ul1"
            enter={(e) => {
              return this.getEnterAnim(e, isMode)
            }}
            leave={{ y: '+=30', opacity: 0, ease: 'easeOutQuad' }}
            id={`${props.id}-ul1`}
          >
            {childrenToRender1}
          </TweenOneGroup>
          </div> */}
        </OverPack>
      </div>
    );
  }
}


export default Content;
