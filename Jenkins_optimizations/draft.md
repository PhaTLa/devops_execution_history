---
theme: uncover
paginate: true
header: 'Jenkins Optimization: A Hands-On Lab'
footer: '© 2025 - The "Jenkins is Fine" Recovery Group'
---

<!-- _class: lead -->

# Jenkins Is Slow, And You're Fired...

### (Just kidding. Let's fix it.)

A Hands-On Lab for Taming a Rogue CI

---

<!-- _class: invert -->

# Part 1: What's That For?

(Do You *Really* Need to Optimize Jenkins?)

---

# "But my Jenkins works fine!" ...Does it?

Optimization isn't a luxury; it's survival. Your "fine" Jenkins is probably costing you more than you think.

*   **The 15-Minute Coffee Break:** When a 2-minute code change takes 15 minutes to build and report back. ☕
*   **The UI Lag:** Clicking "Configure" feels like a long-term commitment.
*   **The Stuck Queue:** 45 builds waiting... 0 executors free... 1 sad developer.
*   **The Dreaded `(offline)`:** That one agent that's always full and given up on life.

This isn't CI/CD. This is CI/C...Wait...D.

---

<!-- _class: invert -->

# Part 2: The "Natural Reflex"

(The "Obvious" Best Practices Everyone Knows)

---

# The "Jenkins 101" Playbook

We all know the basics. Jenkins has been around since... well, forever. These are the "an apple a day" of Jenkins health:

✅ **"Don't build on the master!"**
(You still do it sometimes, don't you? Be honest.)

✅ **"Use webhooks, not polling!"**
(Stop hammering your Git server every 5 minutes.)

✅ **"Discard old builds!"**
(No, you do not need build #17 from 2019.)

✅ **"Use agents!"**
(Duh.)

These are great! But they are not the whole story.

---

<!-- _class: invert -->

# Part 3: Is That All?

(When "Best Practices" Aren't Enough)

---

# The Metrics That *Actually* Matter

"Best practices" are habits. **Metrics** are proof. A healthy Jenkins isn't just *working*, it's *efficient*.

### The Real Bottlenecks We'll Target:

1.  **JVM Heap (Master):** The silent killer. This governs UI speed, Groovy processing, and plugin capacity. If it's full, Jenkins stutters.
2.  **Agent Disk Space:** The new nightmare. Docker images, layers, and un-cleaned workspaces will fill a 6GB disk in *hours*.
3.  **Log Bloat:** Why is your UI slow? It's trying to render 50,000 log lines from one build.
4.  **Artifacts:** Archiving 500MB of... *stuff*... on every build. Back to the master. Uh oh.

---

<!-- _class: invert -->

# Part 4: The Scenario

(Rescuing a "Stroked" Jenkins)

---

# The Patient: Our Lab Setup

Meet our patient. A perfectly normal setup... with a fatal flaw.

*   **Master:** 4GB RAM, 4 vCPU, 45GB Disk
*   **Agent:** 4GB RAM, 2 vCPU, **6GB DISK** (Ominous music)

**The Experiment:**
We will create 3 "Bad" pipelines and set them to run every 5 minutes.

**The Goal:** Intentionally induce a "stroke" within 24 hours.

---

# How to Kill Jenkins (Quickly)

We built a *diabolical* `Jenkinsfile` to attack our key metrics:

**1. Clog the Master (JVM Heap):**
Run heavy Groovy *on the master*.
```groovy
stage('Run on Master (BAD)') {
    agent { label 'master' } 
    // ... create a list of 150,000 UUIDs
}
