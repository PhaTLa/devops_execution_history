---
marp: true
theme: gaia
color: black
_class: 
 - lead
_backgroundImage: url(https://t4.ftcdn.net/jpg/09/28/41/59/360_F_928415910_OtHgmVFFC20kX8W8fR86GvaOb3GYfFYB.jpg)
backgroundImage: url(https://img.freepik.com/free-vector/geometric-abstract-background-with-connected-dots-lines_1409-1866.jpg)
footer: 'AA - Cloud Architecture Team - LGCNS-VNB'
paginate: true
author: Pham Tung Lam
---

![bg left:40% 80%](https://wiki.jenkins-ci.org/JENKINS/attachments/2916393/76054796.svg)

# **JENKINS**
## **OPTIMIZATION** 

###### *Presenter*: Pham Tung Lam (Alex)
---
# AGENDA

1. What that for? Do I need it?
2. Best practices or nature reflex of experiences?
3. Jenkins performance benchmark
4. Pseudo scenario

---
<!--  _class: lead  -->
# 1. What that for?

---
<!--  _header: What that for?  -->

![bg left:40% 80%](https://ih1.redbubble.net/image.639897113.2096/st,small,507x507-pad,600x600,f8f8f8.u2.jpg)

### Let make it clear:

Jenkins is good
- It is realiable
- It is scalable
- (may be) the first CI tool ya know
- A lot of stuff (plugin) to dive in
- Quite simple DSL to learn
- Huge, professional and active community

---
<!--  _header: What that for?  -->
### Have you ever seen it struggle?

![bg : 30%](https://i.programmerhumor.io/2025/01/programmerhumor-io-programming-memes-5185b730ddfff6c.png)

---
<!--  _class: lead  -->
# 2. Best practices or nature reflex of experiences?
---
<!--  _header: Best practices or nature reflex of experiences?  -->

### We all know a thing or two:

- Only build on agent
- Polling is not good for your health - use Webhook
- Log rotation
- History retention policy
- Backup
- Careful with scheduling (quiet period, max execution...)

---
<!--  _header: Best practices or nature reflex of experiences?  -->
<!--  _class: lead  -->
### Those are something your senior taught you.
### Do they actually have impact on Jenkins performance
### Or just to make yar work more professional
---
<!--  _class: lead  -->
# 3. Jenkins performance benchmark
---
<!--  _header: Jenkins performance benchmark  -->
### Have you ever seen :

- A Jenkins instance with hundreds or thousands of pipelines/jobs.
- A Jenkins master working with tens of agents
- A jenkins UI take seconds to response
- History scroll to the abyss
- Build take hour to complete
- Build queue pile up and PM yelling at you

---

<!--  _header: Jenkins performance benchmark  -->
### Turn out, the real bottleneck is:
1.  **JVM Heap (Master):** The silent killer. This governs UI speed, Groovy processing, and plugin capacity.
2.  **Agent Disk Space:**  Docker images, layers, and un-cleaned workspaces will fill a 6GB disk in *hours*.
3.  **Log Bloat:** It's trying to render 50,000 log lines from one build.
4.  **Artifacts:** Archiving MBs of... *stuff*... on every build.

---
<!--  _class: lead  -->
# 4. Pseudo Scenario

### *Rescuring a "stroked" Jenkins*
---
<!--  _header: Pseudo Scenario  -->
### I killed my Jenkins instance, literally!
![bg : 70%](./assets/jenkins_blue_ocean.png)

---
<!--  _header: Pseudo Scenario  -->
### How to:

A faulty pipeline can do the trick!

![bg fit right:60%](./assets/dumy_pipeline.png)

---
<!--  _class: lead  -->
<!--  _header: Pseudo Scenario  -->
### Below is some fresh metric that I collected when init Jenkins 

---

<!--  _header: Pseudo Scenario  -->
### Baseline for comparing:
*System Metrics*
![bg fit right:50% ](./assets/fresh_system_metrics.png)

---
<!--  _header: Pseudo Scenario  -->
### Baseline for comparing:
*GC time*
![bg fit : 80% ](./assets/fresh_GC_time.png)

---
<!--  _header: Pseudo Scenario  -->
### Baseline for comparing:
*Heap usage*
![bg fit : 80% ](./assets/fresh_heap_usage.png)

---
<!--  _header: Pseudo Scenario  -->
### Baseline for comparing:
*Http request meantimes*
![bg fit : 60% ](./assets/fresh_http_request_meantimes.png)

---
<!--  _header: Pseudo Scenario  -->
### Baseline for comparing:
*Job waiting duration*
![bg right : 80% ](./assets/fresh_job_waiting_duration.png)

---
<!--  _header: Pseudo Scenario  -->
### Baseline for comparing:
*Agent free disk*
![bg fit : 50% ](./assets/fresh_free_agent_disk.png)

---
<!--  _class: lead  -->
<!--  _header: Pseudo Scenario  -->
# This is "DEMO" time!!!
*Let's rescure the poor Jenkins instance*