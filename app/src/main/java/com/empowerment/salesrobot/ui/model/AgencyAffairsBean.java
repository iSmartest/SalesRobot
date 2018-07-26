package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/11.
 * Description:
 */
public class AgencyAffairsBean extends BaseBean{
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private List<AgentList> agentList;

        public List<AgentList> getAgentList() {
            return agentList;
        }

        public void setAgentList(List<AgentList> agentList) {
            this.agentList = agentList;
        }

        public static class AgentList {
            private int isRead;
            private long endTime;
            private int id;
            private int isFinish;
            private int type;
            private String content;

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsFinish() {
                return isFinish;
            }

            public void setIsFinish(int isFinish) {
                this.isFinish = isFinish;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
