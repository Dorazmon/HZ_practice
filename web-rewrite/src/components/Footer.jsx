import React from 'react';
import PropTypes from 'prop-types';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';
import QueueAnim from 'rc-queue-anim';
import './footer.less';

class Footer extends React.Component {
  static propTypes = {
    id: PropTypes.string,
  };

  static defaultProps = {
    className: 'footer1',
  };

  getLiChildren = (data, i) => {
    const links = data.contentLink.split(/\n/).filter(item => item);
    const content = data.content.split(/\n/).filter(item => item)
      .map((item, ii) => {
        const cItem = item.trim();
        const isImg = cItem.match(/\.(jpg|png|svg|bmp|jpeg)$/i);
        return (<li className={isImg ? 'icon' : ''} key={ii}>
          <a href={links[ii]} target="_parent">
            {isImg ? <img src={cItem} width="100%" /> : cItem}
          </a>
        </li>);
      });
    return (<li className={data.className} key={i} id={`${this.props.id}-block${i}`}>
        <h2>{data.title}</h2>
        <ul>
          {content}
        </ul>
      </li>);
  }

  render() {
    const props = { ...this.props };
    const isMode = props.isMode;
    delete props.isMode;
    const logoContent = { img: 'https://zos.alipayobjects.com/rmsportal/qqaimmXZVSwAhpL.svg', content: '让 世 界 互 联 更 安 全' };
    const dataSource = [
      //{ title: '产品', content: '产品更新记录\nAPI文档\n快速入门\n参考指南', contentLink: '#\n#\n#\n#' },
      { title: '关于', content: '关于我们\nFAQ\n联系我们', contentLink: '/about\n#' },
      { title: '联系我们', content: '地 址:杭州市中国智慧信息产业园H座506\n联系电话:0571-85358276\n传 真:0571-85133795\n邮 箱:service@tcsec.com.cn', contentLink: '#\n#\n#\n#' },
      //{ title: '关注', content: 'https://zos.alipayobjects.com/rmsportal/IiCDSwhqYwQHLeU.svg\n https://zos.alipayobjects.com/rmsportal/AXtqVjTullNabao.svg\n https://zos.alipayobjects.com/rmsportal/fhJykUTtceAhYFz.svg\n https://zos.alipayobjects.com/rmsportal/IDZTVybHbaKmoEA.svg', contentLink: '#\n#\n#\n#' },
    ];
    const liChildrenToRender = dataSource.map(this.getLiChildren);
    return (<OverPack
      {...props}
      playScale={isMode ? 0.5 : 0.2}
    >
      {/* <QueueAnim type="bottom" component="ul" key="ul" leaveReverse id={`${props.id}-ul`}>
        <li key="logo" id={`${props.id}-logo`}>
          <p className="logo">
            <img src={logoContent.img} width="100%" />
          </p>
          <p>{logoContent.content}</p>
        </li>
        {liChildrenToRender}
        <li><img src="http://www.tcsec.com.cn/wp-content/uploads/2016/09/tcsec%E4%BA%8C%E7%BB%B4%E7%A0%81.jpg" width="60%" style={{marginLeft:70}}/></li>
      </QueueAnim> */}
      <div className="footer_all">
        <div className="footer_content1">
            <QueueAnim type="bottom" component="ul" key="ul" leaveReverse id={`${props.id}-ul`}>
              <li>关于公司</li>
              <li>服务咨询</li>
              <li>新闻中心</li>
              <li>联系我们</li>              
            </QueueAnim>
        </div>
        <div className="footer_content2">
            <div className="footer_img"><img src="/static/img/tcsec.png" /></div>
            <div className="footer_contact">
                <h1>地 址:浙江省杭州市中国智慧信息产业园H座506</h1>
                <h1>联系电话:0571-85358276</h1>
                <h1>邮 箱:service@tcsec.com.cn</h1>
            </div>
        </div>
      </div>
      <TweenOne
        animation={{ y: '+=30', opacity: 0, type: 'from' }}
        key="copyright"
        className="copyright"
        id={`${props.id}-content`}
      >
        <span>
        Copyright © 2014-2017. <a href="#">孝道科技</a>. All Rights Reserved. 
        </span>
      </TweenOne>
      
      
    </OverPack>);
  }
}

export default Footer;
