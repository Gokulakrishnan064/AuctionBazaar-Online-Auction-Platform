// ==========================================================================
// AUCTIONBAZAAR REALISTIC 3D GRAND AUCTION HOUSE ENGINE
// ==========================================================================

const API_BASE = "http://localhost:8080/auction_app";

// ─── WEB AUDIO API SOUND FX SYNTHESIZER ───
let audioCtx = null;
function playAudioFx(type = 'bid') {
  try {
    if (!audioCtx) {
      audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    }
    if (audioCtx.state === 'suspended') {
      audioCtx.resume();
    }

    const osc = audioCtx.createOscillator();
    const gain = audioCtx.createGain();
    osc.connect(gain);
    gain.connect(audioCtx.destination);

    const now = audioCtx.currentTime;

    if (type === 'bid') {
      osc.type = 'sine';
      osc.frequency.setValueAtTime(440, now);
      osc.frequency.exponentialRampToValueAtTime(880, now + 0.15);
      gain.gain.setValueAtTime(0.3, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.15);
      osc.start(now);
      osc.stop(now + 0.15);
    } else if (type === 'win') {
      osc.type = 'triangle';
      osc.frequency.setValueAtTime(523.25, now);
      osc.frequency.setValueAtTime(659.25, now + 0.1);
      osc.frequency.setValueAtTime(783.99, now + 0.2);
      osc.frequency.setValueAtTime(1046.50, now + 0.3);
      gain.gain.setValueAtTime(0.4, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.45);
      osc.start(now);
      osc.stop(now + 0.45);
    } else if (type === 'alert') {
      osc.type = 'sawtooth';
      osc.frequency.setValueAtTime(220, now);
      gain.gain.setValueAtTime(0.2, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.25);
      osc.start(now);
      osc.stop(now + 0.25);
    }
  } catch (e) {
    console.warn('Audio FX not supported:', e);
  }
}

// ─── TOP LIVE TICKER RIBBON ───
function renderLiveTickerBar() {
  if (document.getElementById('liveTickerBar')) return;
  const bar = document.createElement('div');
  bar.id = 'liveTickerBar';
  bar.className = 'live-ticker-wrap';
  
  const itemsHTML = `
    <span class="ticker-item"><i class="bi bi-lightning-charge-fill" style="color:var(--gold);"></i> <strong>LIVE HALL:</strong> @Rahul placed ₹45,000 bid on Rolex Submariner</span>
    <span class="ticker-item"><i class="bi bi-trophy-fill" style="color:var(--emerald);"></i> <strong>WON:</strong> @Priya won Antique Bronze Vase for ₹1,25,000</span>
    <span class="ticker-item"><i class="bi bi-shield-check" style="color:var(--cyan);"></i> <strong>VERIFIED:</strong> 100% Escrow Bidding Guarantee Active</span>
    <span class="ticker-item"><i class="bi bi-fire" style="color:var(--pink);"></i> <strong>HOT:</strong> 4 Live Auctions Closing in < 15 mins</span>
  `;

  bar.innerHTML = `<div class="live-ticker-track">${itemsHTML} ${itemsHTML}</div>`;
  document.body.prepend(bar);
}

// ─── REALISTIC 3D GRAND AUCTION HOUSE THREE.JS ENGINE ───
function init3DAuctionHallCanvas() {
  if (typeof THREE === 'undefined') return;
  if (document.getElementById('three-canvas-container')) return;

  const container = document.createElement('div');
  container.id = 'three-canvas-container';
  document.body.prepend(container);

  // Scene & Camera
  const scene = new THREE.Scene();
  scene.fog = new THREE.FogExp2(0x0A0D14, 0.002);

  const camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 0.1, 1000);
  camera.position.set(0, 40, 320);

  // WebGL Renderer with Tone Mapping & Specular Reflections
  const renderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
  renderer.setSize(window.innerWidth, window.innerHeight);
  renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
  renderer.toneMapping = THREE.ACESFilmicToneMapping;
  renderer.toneMappingExposure = 1.35;
  container.appendChild(renderer.domElement);

  // 1. Realistic Grand Auction Stage Floor
  const floorGeo = new THREE.PlaneGeometry(1200, 1200);
  const floorMat = new THREE.MeshStandardMaterial({
    color: 0x0F172A,
    roughness: 0.25,
    metalness: 0.8,
  });
  const floorMesh = new THREE.Mesh(floorGeo, floorMat);
  floorMesh.rotation.x = -Math.PI / 2;
  floorMesh.position.y = -120;
  scene.add(floorMesh);

  // 2. Realistic Grand Auction House Spotlights
  const goldSpotlight = new THREE.SpotLight(0xF59E0B, 5.0);
  goldSpotlight.position.set(0, 300, 100);
  goldSpotlight.angle = Math.PI / 4;
  goldSpotlight.penumbra = 0.8;
  scene.add(goldSpotlight);

  const cyanSpotlight = new THREE.SpotLight(0x38BDF8, 3.5);
  cyanSpotlight.position.set(-250, 250, -50);
  cyanSpotlight.angle = Math.PI / 4;
  cyanSpotlight.penumbra = 0.9;
  scene.add(cyanSpotlight);

  const violetSpotlight = new THREE.SpotLight(0x8B5CF6, 3.5);
  violetSpotlight.position.set(250, 250, -50);
  violetSpotlight.angle = Math.PI / 4;
  violetSpotlight.penumbra = 0.9;
  scene.add(violetSpotlight);

  // 3. Floating Realistic Gold Auction Gavels & Pedestal Decor
  const objectsGroup = new THREE.Group();
  const floatingItems = [];

  // PBR Gold Metallic & Glass Crystals
  const goldMat = new THREE.MeshStandardMaterial({ color: 0xF59E0B, metalness: 0.9, roughness: 0.15 });
  const glassMat = new THREE.MeshPhysicalMaterial({ color: 0x38BDF8, transmission: 0.9, roughness: 0.1, transparent: true, opacity: 0.85 });

  // Floating 3D Gold Rings & Crystals
  const torusGeo = new THREE.TorusGeometry(26, 4, 16, 100);
  const octaGeo = new THREE.OctahedronGeometry(28, 0);

  for (let i = 0; i < 5; i++) {
    const ring = new THREE.Mesh(torusGeo, goldMat);
    ring.position.set((Math.random() - 0.5) * 650, (Math.random() - 0.5) * 350 + 20, (Math.random() - 0.5) * 250);
    ring.rotation.set(Math.random() * Math.PI, Math.random() * Math.PI, 0);
    objectsGroup.add(ring);
    floatingItems.push({ mesh: ring, rotX: 0.005, rotY: 0.007, offsetY: Math.random() * Math.PI * 2 });

    const crystal = new THREE.Mesh(octaGeo, glassMat);
    crystal.position.set((Math.random() - 0.5) * 650, (Math.random() - 0.5) * 350 + 20, (Math.random() - 0.5) * 250);
    crystal.rotation.set(Math.random() * Math.PI, Math.random() * Math.PI, 0);
    objectsGroup.add(crystal);
    floatingItems.push({ mesh: crystal, rotX: 0.006, rotY: 0.008, offsetY: Math.random() * Math.PI * 2 });
  }

  scene.add(objectsGroup);

  // Mouse Interaction Parallax Tracking
  let mouseX = 0;
  let mouseY = 0;

  document.addEventListener('mousemove', (e) => {
    mouseX = (e.clientX - window.innerWidth / 2);
    mouseY = (e.clientY - window.innerHeight / 2);
  });

  let time = 0;
  function animate() {
    requestAnimationFrame(animate);
    time += 0.015;

    floatingItems.forEach(item => {
      item.mesh.rotation.x += item.rotX;
      item.mesh.rotation.y += item.rotY;
      item.mesh.position.y += Math.sin(time + item.offsetY) * 0.35;
    });

    goldSpotlight.position.x = mouseX * 0.4;
    camera.position.x += (mouseX * 0.04 - camera.position.x) * 0.04;
    camera.position.y += (-mouseY * 0.04 + 40 - camera.position.y) * 0.04;
    camera.lookAt(0, 0, 0);

    renderer.render(scene, camera);
  }

  animate();

  window.addEventListener('resize', () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
  });
}

// ─── DYNAMIC AMBIENT STAGE BACKDROP ENGINE ───
function applyDynamicProductStage(imgEl) {
  if (!imgEl) return;
  const parent = imgEl.parentElement;
  if (!parent) return;

  const src = imgEl.src;
  if (!src || src.includes('placeholder')) return;

  let backdrop = parent.querySelector('.card-img-backdrop');
  if (!backdrop) {
    backdrop = document.createElement('div');
    backdrop.className = 'card-img-backdrop';
    parent.insertBefore(backdrop, parent.firstChild);
  }
  backdrop.style.backgroundImage = `url("${src}")`;
}

function initAllProductStageBackdrops() {
  document.querySelectorAll('.card-img-wrapper img, #imgWrapper img, .detail-img-card-3d img').forEach(img => {
    if (img.complete && img.naturalWidth !== 0) {
      applyDynamicProductStage(img);
    } else {
      img.addEventListener('load', () => applyDynamicProductStage(img));
    }
  });
}

// ─── DYNAMIC CATEGORY STAGE HELPER ───
function getCategorySlug(title = '', category = '') {
  const text = (title + ' ' + category).toLowerCase();
  if (text.includes('watch') || text.includes('clock') || text.includes('rolex')) return 'watches';
  if (text.includes('car') || text.includes('vehicle') || text.includes('bike') || text.includes('bmw') || text.includes('porsche')) return 'vehicles';
  if (text.includes('art') || text.includes('painting') || text.includes('sculpture')) return 'art';
  if (text.includes('ring') || text.includes('diamond') || text.includes('gem') || text.includes('necklace')) return 'jewelry';
  return 'general';
}

// ─── AUTH HELPERS ───
function getUser() {
  try { return JSON.parse(localStorage.getItem("ab_user")); }
  catch { return null; }
}

function setUser(user) {
  localStorage.setItem("ab_user", JSON.stringify(user));
}

function logout() {
  localStorage.removeItem("ab_user");
  playAudioFx('alert');
  showToast("Logged out successfully", "success");
  setTimeout(() => { window.location.href = "index.html"; }, 600);
}

function requireAuth() {
  if (!getUser()) { window.location.href = "login.html"; }
}

// ─── NAVBAR & FOOTER RENDERING ───
function renderNavbar(activePage) {
  renderLiveTickerBar();
  const user = getUser();
  const navEl = document.getElementById("mainNavbar");
  if (!navEl) return;

  let authLinks = user ? `
    <li class="nav-item"><a class="nav-link ${activePage==='my-bids'?'active':''}" href="my-bids.html"><i class="bi bi-hammer me-1"></i>My Bids</a></li>
    <li class="nav-item"><a class="nav-link ${activePage==='create-auction'?'active':''}" href="create-auction.html"><i class="bi bi-plus-circle me-1"></i>Sell</a></li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
        <i class="bi bi-person-circle me-1"></i>${user.userName || user.username || user.firstName || 'Account'}
      </a>
      <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end" style="background:rgba(15,23,42,0.95);border:1px solid rgba(56,189,248,0.3);backdrop-filter:blur(16px);">
        <li><a class="dropdown-item" href="profile.html" style="color:var(--text-secondary);font-size:.88rem;"><i class="bi bi-person me-2"></i>Profile</a></li>
        <li><a class="dropdown-item" href="admin-dashboard.html" style="color:var(--text-secondary);font-size:.88rem;"><i class="bi bi-speedometer2 me-2"></i>Dashboard</a></li>
        <li><hr class="dropdown-divider" style="border-color:var(--border-light);"></li>
        <li><a class="dropdown-item" href="#" onclick="logout()" style="color:var(--pink);font-size:.88rem;"><i class="bi bi-box-arrow-right me-2"></i>Logout</a></li>
      </ul>
    </li>
  ` : `
    <li class="nav-item"><a class="nav-link btn-nav-login ms-2" href="login.html">Login</a></li>
    <li class="nav-item"><a class="nav-link btn-nav-cta ms-2" href="register.html">Register</a></li>
  `;

  navEl.innerHTML = `
    <div class="container">
      <a class="navbar-brand navbar-brand-custom" href="index.html">
        <img src="images/logo.png" alt="AuctionBazaar GK Logo" style="height:36px;width:auto;object-fit:contain;filter:drop-shadow(0 2px 8px rgba(245,158,11,0.4));" class="me-2">AuctionBazaar
      </a>
      <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navCollapse">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navCollapse">
        <ul class="navbar-nav me-auto align-items-center">
          <li class="nav-item"><a class="nav-link ${activePage==='home'?'active':''}" href="index.html">Home</a></li>
          <li class="nav-item"><a class="nav-link ${activePage==='auctions'?'active':''}" href="auctions.html">Explore Auctions</a></li>
          <li class="nav-item ms-lg-2 mt-2 mt-lg-0">
            <button class="cmd-k-trigger" onclick="toggleCmdPalette()"><i class="bi bi-search"></i>Search <kbd>Ctrl K</kbd></button>
          </li>
        </ul>
        <ul class="navbar-nav align-items-center gap-1">${authLinks}</ul>
      </div>
    </div>
  `;
}

function renderFooter() {
  const el = document.getElementById("mainFooter");
  if (!el) return;
  el.innerHTML = `
    <footer class="footer-auction">
      <div class="container">
        <div class="row g-4">
          <div class="col-md-4">
            <div class="footer-brand mb-2 d-flex align-items-center gap-2"><img src="images/logo.png" alt="AuctionBazaar GK Logo" style="height:36px;width:auto;filter:drop-shadow(0 2px 8px rgba(245,158,11,0.3));">AuctionBazaar</div>
            <p class="footer-text">The realistic 3D grand auction house. Real-time bidding, PBR stage environments, instant search, and verified items.</p>
          </div>
          <div class="col-md-2">
            <p class="footer-text fw-600 mb-2" style="color:var(--gold);font-size:.85rem;text-transform:uppercase;letter-spacing:.5px;">Marketplace</p>
            <a href="auctions.html" class="footer-link">Live Auctions</a>
            <a href="create-auction.html" class="footer-link">List Item</a>
          </div>
          <div class="col-md-2">
            <p class="footer-text fw-600 mb-2" style="color:var(--gold);font-size:.85rem;text-transform:uppercase;letter-spacing:.5px;">Account</p>
            <a href="profile.html" class="footer-link">User Profile</a>
            <a href="my-bids.html" class="footer-link">My Bids</a>
          </div>
          <div class="col-md-4">
            <p class="footer-text fw-600 mb-2" style="color:var(--gold);font-size:.85rem;text-transform:uppercase;letter-spacing:.5px;">Support</p>
            <p class="footer-text"><i class="bi bi-envelope me-2" style="color:var(--gold);"></i>support@auctionbazaar.com</p>
            <p class="footer-text"><i class="bi bi-shield-check me-2" style="color:var(--emerald);"></i>100% Encrypted Bidding Engine</p>
          </div>
        </div>
        <hr class="divider mt-4" style="border-color:var(--border-light);">
        <p class="footer-text text-center mt-3">© 2026 AuctionBazaar Platform. All rights reserved.</p>
      </div>
    </footer>
  `;
}

// ─── 3D CARD TILT PHYSICS ENGINE ───
function init3DTiltCards() {
  document.querySelectorAll('.auction-card').forEach(card => {
    if (card.dataset.tiltInitialized) return;
    card.dataset.tiltInitialized = 'true';

    card.addEventListener('mousemove', (e) => {
      const rect = card.getBoundingClientRect();
      const x = e.clientX - rect.left;
      const y = e.clientY - rect.top;
      const centerX = rect.width / 2;
      const centerY = rect.height / 2;
      
      const rotateX = ((y - centerY) / centerY) * -10;
      const rotateY = ((x - centerX) / centerX) * 10;

      card.style.transform = `perspective(1200px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateZ(12px)`;
    });

    card.addEventListener('mouseleave', () => {
      card.style.transform = `perspective(1200px) rotateX(0deg) rotateY(0deg) translateZ(0px)`;
    });
  });
}

// ─── CURSOR SPOTLIGHT ───
function initCursorSpotlight() {
  if (document.getElementById('cursor-spotlight')) return;
  const spotlight = document.createElement('div');
  spotlight.id = 'cursor-spotlight';
  document.body.prepend(spotlight);

  document.addEventListener('mousemove', (e) => {
    spotlight.style.setProperty('--mouse-x', `${e.clientX}px`);
    spotlight.style.setProperty('--mouse-y', `${e.clientY}px`);
  });
}

// ─── COMMAND PALETTE (CTRL + K) ───
let allCmdAuctions = [];
function toggleCmdPalette() {
  playAudioFx('bid');
  let modalEl = document.getElementById('cmdPaletteModal');
  if (!modalEl) {
    modalEl = document.createElement('div');
    modalEl.id = 'cmdPaletteModal';
    modalEl.className = 'modal fade modal-cyber';
    modalEl.tabIndex = -1;
    modalEl.innerHTML = `
      <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
          <div class="modal-header border-0 pb-0">
            <div class="input-group">
              <span class="input-group-text bg-transparent border-0 text-gold"><i class="bi bi-search fs-5"></i></span>
              <input type="text" id="cmdInput" class="form-control form-control-auction border-0 fs-5 ps-0" placeholder="Type to search items, categories..." autocomplete="off">
            </div>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body py-3" style="max-height:400px;overflow-y:auto;" id="cmdResults">
            <div class="text-center py-4 text-muted"><i class="bi bi-search fs-2 d-block mb-2"></i>Search live auctions across AuctionBazaar</div>
          </div>
          <div class="modal-footer border-0 pt-0 justify-content-between text-muted" style="font-size:.78rem;">
            <span><kbd>ESC</kbd> to exit</span>
            <span><i class="bi bi-lightning-fill me-1" style="color:var(--gold);"></i>Instant Search Engine</span>
          </div>
        </div>
      </div>
    `;
    document.body.appendChild(modalEl);

    document.getElementById('cmdInput').addEventListener('input', (e) => {
      const q = e.target.value.toLowerCase().trim();
      const resContainer = document.getElementById('cmdResults');
      if (!q) {
        resContainer.innerHTML = `<div class="text-center py-4 text-muted"><i class="bi bi-search fs-2 d-block mb-2"></i>Search live auctions across AuctionBazaar</div>`;
        return;
      }
      const filtered = allCmdAuctions.filter(a => (a.title && a.title.toLowerCase().includes(q)) || (a.description && a.description.toLowerCase().includes(q)) || (a.category && a.category.toLowerCase().includes(q)));
      if (filtered.length === 0) {
        resContainer.innerHTML = `<div class="text-center py-4 text-muted"><i class="bi bi-emoji-frown fs-2 d-block mb-2"></i>No matching auctions found for "${q}"</div>`;
        return;
      }
      resContainer.innerHTML = filtered.map(a => {
        const img = a.imagePath ? `${API_BASE}/upload/${a.imagePath}` : (a.imageUrl || 'https://via.placeholder.com/80');
        return `
          <div class="d-flex align-items-center justify-content-between p-2 rounded mb-2" style="background:rgba(255,255,255,0.03);cursor:pointer;transition:var(--transition);" onclick="window.location.href='auction-details.html?id=${a.id}'" onmouseover="this.style.background='rgba(245,158,11,0.15)'" onmouseout="this.style.background='rgba(255,255,255,0.03)'">
            <div class="d-flex align-items-center gap-3">
              <img src="${img}" style="width:48px;height:48px;object-fit:cover;border-radius:8px;">
              <div>
                <div class="fw-bold text-capitalize">${a.title}</div>
                <div class="text-muted" style="font-size:.78rem;">${a.category || 'General'}</div>
              </div>
            </div>
            <div class="text-end">
              <div class="fw-bold" style="color:var(--gold);">${formatCurrency(a.currentPrice || a.basePrice)}</div>
              <span class="status-badge-live" style="font-size:.65rem;padding:.15rem .5rem;">View</span>
            </div>
          </div>
        `;
      }).join('');
    });
  }

  const bsModal = bootstrap.Modal.getOrCreateInstance(modalEl);
  bsModal.toggle();

  if (allCmdAuctions.length === 0) {
    apiCall('/auction/findAllAuction').then(res => {
      allCmdAuctions = res.data?.auctions || res.data?.allAuction || [];
    });
  }

  setTimeout(() => {
    const input = document.getElementById('cmdInput');
    if (input) input.focus();
  }, 250);
}

document.addEventListener('keydown', (e) => {
  if ((e.ctrlKey || e.metaKey) && e.key.toLowerCase() === 'k') {
    e.preventDefault();
    toggleCmdPalette();
  }
});

// ─── QUICK VIEW MODAL DRAWER ───
async function openQuickView(auctionId) {
  playAudioFx('bid');
  let modalEl = document.getElementById('quickViewModal');
  if (!modalEl) {
    modalEl = document.createElement('div');
    modalEl.id = 'quickViewModal';
    modalEl.className = 'modal fade modal-cyber';
    modalEl.tabIndex = -1;
    modalEl.innerHTML = `
      <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content" id="quickViewContent">
          <div class="modal-body text-center py-5">
            <div class="spinner-border text-gold" role="status"></div>
          </div>
        </div>
      </div>
    `;
    document.body.appendChild(modalEl);
  }

  const bsModal = bootstrap.Modal.getOrCreateInstance(modalEl);
  bsModal.show();

  const res = await apiCall(`/auction/findById/${auctionId}`);
  const content = document.getElementById('quickViewContent');

  if (!res.ok || !res.data) {
    content.innerHTML = `<div class="modal-body text-center py-5 text-muted"><p>Failed to load auction details.</p></div>`;
    return;
  }

  const a = res.data;
  const imgUrl = a.imagePath ? `${API_BASE}/upload/${a.imagePath}` : (a.imageUrl || 'https://via.placeholder.com/400');
  const price = formatCurrency(a.currentPrice || a.basePrice || 0);
  const catSlug = getCategorySlug(a.title, a.category);

  content.innerHTML = `
    <div class="modal-header border-0 pb-0">
      <span class="status-badge-live">Live Quick View</span>
      <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
    </div>
    <div class="modal-body p-4">
      <div class="row g-4 align-items-center">
        <div class="col-md-6">
          <div class="card-img-wrapper rounded-3 overflow-hidden shadow" data-category="${catSlug}">
            <img src="${imgUrl}" alt="${a.title}" style="width:100%;height:260px;object-fit:cover;">
          </div>
          <div class="d-flex align-items-center justify-content-between mt-2 px-1">
            <span class="seller-trust-badge"><i class="bi bi-patch-check-fill"></i> Verified Seller</span>
            <span class="text-muted" style="font-size:.78rem;"><i class="bi bi-star-fill me-1" style="color:var(--gold);"></i> 4.9 (120+ sales)</span>
          </div>
        </div>
        <div class="col-md-6">
          <h3 class="mb-1 text-capitalize">${a.title}</h3>
          <p class="text-muted mb-3" style="font-size:.9rem;">${a.description || 'No detailed description available.'}</p>
          <div class="p-3 mb-3 rounded" style="background:rgba(255,255,255,0.03);border:1px solid var(--border-light);">
            <div class="card-price-label">Current Highest Bid</div>
            <div class="card-price fs-2" style="color:var(--gold);">${price}</div>
          </div>
          <div class="d-flex gap-2">
            <a href="auction-details.html?id=${a.id}" class="btn btn-gold flex-grow-1"><i class="bi bi-hammer me-2"></i>Full Bid Console</a>
            <button class="btn btn-outline-gold" onclick="launchConfetti(); playAudioFx('win'); showToast('Interest saved!','success');"><i class="bi bi-heart"></i></button>
          </div>
        </div>
      </div>
    </div>
  `;
}

// ─── CONFETTI CELEBRATION EFFECT ───
function launchConfetti() {
  playAudioFx('win');
  let canvas = document.getElementById('confettiCanvas');
  if (!canvas) {
    canvas = document.createElement('canvas');
    canvas.id = 'confettiCanvas';
    document.body.appendChild(canvas);
  }
  const ctx = canvas.getContext('2d');
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;

  const particles = [];
  const colors = ['#F59E0B', '#38BDF8', '#8B5CF6', '#10B981', '#EC4899'];

  for (let i = 0; i < 110; i++) {
    particles.push({
      x: canvas.width / 2,
      y: canvas.height / 2,
      vx: (Math.random() - 0.5) * 16,
      vy: (Math.random() - 0.8) * 16,
      size: Math.random() * 8 + 4,
      color: colors[Math.floor(Math.random() * colors.length)],
      alpha: 1
    });
  }

  let ticks = 0;
  function render() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    particles.forEach(p => {
      p.x += p.vx;
      p.y += p.vy;
      p.vy += 0.22;
      p.alpha -= 0.015;

      ctx.fillStyle = p.color;
      ctx.globalAlpha = Math.max(0, p.alpha);
      ctx.fillRect(p.x, p.y, p.size, p.size);
    });

    ticks++;
    if (ticks < 70) requestAnimationFrame(render);
    else ctx.clearRect(0, 0, canvas.width, canvas.height);
  }

  render();
}

// ─── COUNTDOWN TIMER ───
function startCountdown(endDateStr, elementId) {
  const el = document.getElementById(elementId);
  if (!el) return;

  function update() {
    const now = new Date().getTime();
    const end = new Date(endDateStr).getTime();
    const diff = end - now;

    if (diff <= 0) {
      el.innerHTML = `<span style="color:var(--pink);font-size:.9rem;font-weight:700;">Auction Closed</span>`;
      return;
    }

    const d = Math.floor(diff / (1000 * 60 * 60 * 24));
    const h = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const m = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    const s = Math.floor((diff % (1000 * 60)) / 1000);

    el.innerHTML = `
      <div class="countdown-box">
        <div class="countdown-unit"><div class="countdown-number">${String(d).padStart(2,'0')}</div><div class="countdown-label">Days</div></div>
        <div class="countdown-sep">:</div>
        <div class="countdown-unit"><div class="countdown-number">${String(h).padStart(2,'0')}</div><div class="countdown-label">Hrs</div></div>
        <div class="countdown-sep">:</div>
        <div class="countdown-unit"><div class="countdown-number">${String(m).padStart(2,'0')}</div><div class="countdown-label">Min</div></div>
        <div class="countdown-sep">:</div>
        <div class="countdown-unit"><div class="countdown-number">${String(s).padStart(2,'0')}</div><div class="countdown-label">Sec</div></div>
      </div>
    `;
  }

  update();
  setInterval(update, 1000);
}

// ─── FORMAT CURRENCY & DATE ───
function formatCurrency(amount) {
  const val = parseFloat(amount || 0);
  return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 0 }).format(val);
}

function formatDate(dateStr) {
  if (!dateStr) return '—';
  return new Date(dateStr).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}

// ─── TOAST MESSAGES ───
function showToast(message, type = 'success') {
  if (type === 'success') playAudioFx('bid');
  else playAudioFx('alert');

  let container = document.getElementById('toastContainer');
  if (!container) {
    container = document.createElement('div');
    container.id = 'toastContainer';
    container.style.cssText = 'position:fixed;top:1.5rem;right:1.5rem;z-index:99999;display:flex;flex-direction:column;gap:.6rem;';
    document.body.appendChild(container);
  }

  const toast = document.createElement('div');
  const bg = type === 'success' ? 'rgba(16,185,129,0.2)' : 'rgba(236,72,153,0.2)';
  const color = type === 'success' ? 'var(--emerald)' : 'var(--pink)';
  const border = type === 'success' ? 'rgba(16,185,129,0.5)' : 'rgba(236,72,153,0.5)';
  const icon = type === 'success' ? 'bi-check-circle-fill' : 'bi-exclamation-triangle-fill';

  toast.style.cssText = `background:${bg};border:1px solid ${border};color:${color};padding:.8rem 1.4rem;border-radius:12px;font-size:.9rem;font-weight:600;display:flex;align-items:center;gap:.7rem;min-width:260px;backdrop-filter:blur(16px);box-shadow:0 10px 30px rgba(0,0,0,0.5);opacity:0;transform:translateX(30px);transition:all .35s cubic-bezier(0.16,1,0.3,1);`;
  toast.innerHTML = `<i class="bi ${icon}" style="font-size:1.1rem;"></i>${message}`;
  container.appendChild(toast);

  requestAnimationFrame(() => { toast.style.opacity = '1'; toast.style.transform = 'translateX(0)'; });
  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateX(30px)';
    setTimeout(() => toast.remove(), 350);
  }, 3800);
}

// ─── API CALL HELPER ───
async function apiCall(endpoint, options = {}) {
  try {
    const response = await fetch(`${API_BASE}${endpoint}`, options);
    const data = await response.json();
    return { ok: response.ok, status: response.status, data };
  } catch (err) {
    console.error('API Error:', err);
    return { ok: false, error: err.message };
  }
}

// Auto Initialize Realistic 3D Auction House Engine on load
document.addEventListener('DOMContentLoaded', () => {
  renderLiveTickerBar();
  initCursorSpotlight();
  init3DAuctionHallCanvas();
  setTimeout(init3DTiltCards, 300);
  initAllProductStageBackdrops();
  setInterval(initAllProductStageBackdrops, 600);
});
