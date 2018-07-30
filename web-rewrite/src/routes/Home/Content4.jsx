import React, { Component } from 'react';
import PropTypes from 'prop-types';
import TweenOne, { TweenOneGroup } from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';
import $ from 'jquery';

class Content extends Component {

  static propTypes = {
    id: PropTypes.string,
  };

  static defaultProps = {
    className: 'content4',
  };

  componentDidMount(){
    // myAddEvent(window,'load',scrollImg);
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
    //   next();
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
        <a href={item.src1}><p>{item.content1}</p></a>
      </div>
      <div className="content-wrapper">
        <span><img src={item.img2} height="100%" /></span>
        <a href={item.src2}><p>{item.content2}</p></a>
      </div>
    </li>);
  }

  getEnterAnim = (e, isMode) => {
    const index = e.index;
    const delay = isMode ? index * 50 + 200 : index % 4 * 100 + Math.floor(index / 4) * 100 + 300;
    return { y: '+=30', opacity: 0, type: 'from', delay };
  };

  imgscroll = () => {
    myAddEvent(window,'load',scrollImg);
    function scrollImg(){
      function next(){

        if(i<aPicList.length){
          new ScrollImg(aPicList[i]);
          window.timer = setTimeout(next, 0);
        }
        i++;
        if(i==aPicList.length){
          clearTimeout();
        }
      }
      const aPicList=getByClass(document,'pic-list');
      let i=0;
      next();
    }
  }

  render() {
    const props = { ...this.props };
    const isMode = props.isMode;
    delete props.isMode;
    const dataArray = [
      { img1: 'static/user/chuanhua.png', content1: '传化集团', src1: 'http://www.etransfar.com/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h2.png', content1: '海尔集团', src1: 'http://www.haier.com/cn/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h5.png', content1: '海康威视', src1: 'http://www.hikvision.com/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h4.png', content1: '连连支付', src1: 'http://www.lianlianpay.com/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h3.png', content1: '鑫合汇', src1: 'https://www.xinhehui.com/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h6.png', content1: '浙江物产', src1: 'http://www.zjmi.com.cn', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h7.png', content1: '浙江省农业科学院', src1: 'http://www.zaas.ac.cn/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h8.png', content1: '更多', src1: 'services/web-penetration', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h2.png', content1: '海尔集团', src1: 'http://www.haier.com/cn/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h5.png', content1: '海康威视', src1: 'http://www.hikvision.com/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h4.png', content1: '连连支付', src1: 'http://www.lianlianpay.com/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h3.png', content1: '鑫合汇', src1: 'https://www.xinhehui.com/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h6.png', content1: '浙江物产', src1: 'http://www.zjmi.com.cn', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h7.png', content1: '浙江省农业科学院', src1: 'http://www.zaas.ac.cn/', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
      { img1: 'static/user/h8.png', content1: '更多', src1: 'services/web-penetration', img2: 'static/user/chuanhua.png', content2: '传化集团', src2: 'http://www.etransfar.com/' },
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
          >
            <h1>我们的用户</h1>
            <p>Our Users</p>
          </TweenOne>
          <div className="img-list">
          <TweenOneGroup
            className={`${props.className}-img-wrapper`}
            component="ul"
            key="ul"
            enter={(e) => {
              return (this.getEnterAnim(e, isMode))
            }}
            leave={{ y: '+=30', opacity: 0, ease: 'easeOutQuad' }}
            id={`${props.id}-ul`}
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
